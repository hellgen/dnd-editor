package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSkillRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSkillResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.CharacterSkill;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceAbilityBonus;
import com.helen.dnd_charachter_editor.entity.reference.table.Skill;
import com.helen.dnd_charachter_editor.entity.reference.table.SubraceAbilityBonus;
import com.helen.dnd_charachter_editor.mapper.character.CharacterSkillMapper;
import com.helen.dnd_charachter_editor.repository.character.CharacterAbilityRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterSkillRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.AbilityRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.RaceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.SkillRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.SubraceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.service.auth.AuthService;
import com.helen.dnd_charachter_editor.service.character.CharacterSkillService;
import com.helen.dnd_charachter_editor.service.character.DndRulesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCharacterSkillService implements CharacterSkillService {

    private final AuthService authService;
    private final CharacterRepository characterRepository;
    private final CharacterSkillRepository characterSkillRepository;
    private final CharacterAbilityRepository characterAbilityRepository;
    private final SkillRepository skillRepository;
    private final AbilityRepository abilityRepository;
    private final RaceAbilityBonusRepository raceAbilityBonusRepository;
    private final SubraceAbilityBonusRepository subraceAbilityBonusRepository;
    private final DndRulesService dndRulesService;

    @Override
    @Transactional(readOnly = true)
    public List<CharacterSkillResponse> getCharacterSkills(UUID characterId) {
        UserCharacter character = getCurrentUserCharacter(characterId);
        CharacterSkillCalculationContext context = buildCalculationContext(character);

        return characterSkillRepository.findAllByCharacterId(characterId)
                .stream()
                .sorted(Comparator.comparing(characterSkill -> characterSkill.getSkill().getName()))
                .map(characterSkill -> toResponse(characterSkill, context))
                .toList();
    }

    @Override
    @Transactional
    public CharacterSkillResponse updateCharacterSkill(
            UUID characterId,
            UUID skillId,
            SetCharacterSkillRequest request
    ) {
        UserCharacter character = getCurrentUserCharacter(characterId);
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("Навык не найден"));

        validateProficiencyLevel(request.proficiencyLevel());

        CharacterSkill characterSkill = characterSkillRepository
                .findByCharacterIdAndSkillId(characterId, skillId)
                .orElseGet(() -> CharacterSkillMapper.toEntity(character, skill));

        characterSkill.setCharacter(character);
        characterSkill.setSkill(skill);
        characterSkill.setProficiencyLevel(request.proficiencyLevel());

        CharacterSkill savedCharacterSkill = characterSkillRepository.save(characterSkill);
        CharacterSkillCalculationContext context = buildCalculationContext(character);

        return toResponse(savedCharacterSkill, context);
    }

    private UserCharacter getCurrentUserCharacter(UUID characterId) {
        User user = authService.getCurrentUser();
        return characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Персонаж не найден"));
    }

    private void validateProficiencyLevel(Integer proficiencyLevel) {
        if (proficiencyLevel == null || proficiencyLevel < 0 || proficiencyLevel > 2) {
            throw new IllegalArgumentException("proficiencyLevel должен быть в диапазоне 0..2");
        }
    }

    private CharacterSkillResponse toResponse(
            CharacterSkill characterSkill,
            CharacterSkillCalculationContext context
    ) {
        Skill skill = characterSkill.getSkill();
        Integer abilityModifier = calculateAbilityModifier(skill, context);
        Integer proficiencyBonus = context.proficiencyBonus();
        Integer proficiencyLevel = characterSkill.getProficiencyLevel();
        Integer totalModifier = abilityModifier + proficiencyBonus * proficiencyLevel;

        return new CharacterSkillResponse(
                characterSkill.getId(),
                characterSkill.getCharacter().getId(),
                skill.getId(),
                skill.getName(),
                skill.getAbility(),
                abilityModifier,
                proficiencyLevel,
                proficiencyBonus,
                totalModifier
        );
    }

    private Integer calculateAbilityModifier(
            Skill skill,
            CharacterSkillCalculationContext context
    ) {
        Ability ability = findAbilityForSkill(skill, context.abilitiesByNormalizedCode())
                .orElseThrow(() -> new EntityNotFoundException("Характеристика навыка не найдена"));
        CharacterAbility characterAbility = context.characterAbilitiesByAbilityId().get(ability.getId());
        Integer baseValue = characterAbility != null ? characterAbility.getValue() : 0;
        Integer finalValue = baseValue
                + context.raceBonusesByAbilityId().getOrDefault(ability.getId(), 0)
                + context.subraceBonusesByAbilityId().getOrDefault(ability.getId(), 0);

        return dndRulesService.calculateAbilityModifier(finalValue);
    }

    private Optional<Ability> findAbilityForSkill(
            Skill skill,
            Map<String, Ability> abilitiesByNormalizedCode
    ) {
        return Optional.ofNullable(abilitiesByNormalizedCode.get(normalizeAbilityCode(skill.getAbility())));
    }

    private CharacterSkillCalculationContext buildCalculationContext(UserCharacter character) {
        Map<String, Ability> abilitiesByNormalizedCode = abilityRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        ability -> normalizeAbilityCode(ability.getCode()),
                        Function.identity()
                ));
        Map<UUID, CharacterAbility> characterAbilitiesByAbilityId = characterAbilityRepository
                .findAllByCharacterId(character.getId())
                .stream()
                .collect(Collectors.toMap(
                        characterAbility -> characterAbility.getAbility().getId(),
                        Function.identity()
                ));
        Map<UUID, Integer> raceBonusesByAbilityId = getRaceBonuses(character);
        Map<UUID, Integer> subraceBonusesByAbilityId = getSubraceBonuses(character);
        Integer proficiencyBonus = dndRulesService.calculateProficiencyBonus(character.getLevel());

        return new CharacterSkillCalculationContext(
                abilitiesByNormalizedCode,
                characterAbilitiesByAbilityId,
                raceBonusesByAbilityId,
                subraceBonusesByAbilityId,
                proficiencyBonus
        );
    }

    private Map<UUID, Integer> getRaceBonuses(UserCharacter character) {
        UUID raceId = character.getRace().getId();

        return raceAbilityBonusRepository.findAllByRaceId(raceId)
                .stream()
                .collect(Collectors.toMap(
                        raceAbilityBonus -> raceAbilityBonus.getAbility().getId(),
                        RaceAbilityBonus::getBonusValue
                ));
    }

    private Map<UUID, Integer> getSubraceBonuses(UserCharacter character) {
        if (character.getSubrace() == null) {
            return Map.of();
        }

        UUID subraceId = character.getSubrace().getId();

        return subraceAbilityBonusRepository.findAllBySubraceId(subraceId)
                .stream()
                .collect(Collectors.toMap(
                        subraceAbilityBonus -> subraceAbilityBonus.getAbility().getId(),
                        SubraceAbilityBonus::getBonusValue
                ));
    }

    private String normalizeAbilityCode(String abilityCode) {
        if (abilityCode == null) {
            return null;
        }

        String normalizedAbilityCode = abilityCode.trim().toUpperCase();

        return switch (normalizedAbilityCode) {
            case "STR", "STRENGTH" -> "STR";
            case "DEX", "DEXTERITY" -> "DEX";
            case "CON", "CONSTITUTION" -> "CON";
            case "INT", "INTELLIGENCE" -> "INT";
            case "WIS", "WISDOM" -> "WIS";
            case "CHA", "CHARISMA" -> "CHA";
            default -> normalizedAbilityCode;
        };
    }

    private record CharacterSkillCalculationContext(
            Map<String, Ability> abilitiesByNormalizedCode,
            Map<UUID, CharacterAbility> characterAbilitiesByAbilityId,
            Map<UUID, Integer> raceBonusesByAbilityId,
            Map<UUID, Integer> subraceBonusesByAbilityId,
            Integer proficiencyBonus
    ) {
    }
}
