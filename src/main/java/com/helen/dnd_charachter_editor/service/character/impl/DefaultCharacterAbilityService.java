package com.helen.dnd_charachter_editor.service.character.impl;


import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilitiesRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityValueRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceAbilityBonus;
import com.helen.dnd_charachter_editor.entity.reference.table.SubraceAbilityBonus;
import com.helen.dnd_charachter_editor.mapper.character.CharacterAbilityMapper;
import com.helen.dnd_charachter_editor.repository.character.CharacterAbilityRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.AbilityRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.RaceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.SubraceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.service.auth.AuthService;
import com.helen.dnd_charachter_editor.service.character.CharacterAbilityService;
import com.helen.dnd_charachter_editor.service.character.DndRulesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCharacterAbilityService implements CharacterAbilityService {

    private final AuthService authService;
    private final CharacterRepository characterRepository;
    private final AbilityRepository abilityRepository;
    private final CharacterAbilityRepository characterAbilityRepository;
    private final RaceAbilityBonusRepository raceAbilityBonusRepository;
    private final SubraceAbilityBonusRepository subraceAbilityBonusRepository;
    private final DndRulesService dndRulesService;

    @Override
    @Transactional
    public CharacterAbilityResponse setCharacterAbility(UUID characterId, UUID abilityId, SetCharacterAbilityRequest request) {
        UserCharacter character = getCurrentUserCharacter(characterId);
        Ability ability = getAbility(abilityId);
        validateCharacterRaceAndSubrace(character);

        CharacterAbility characterAbility = characterAbilityRepository
                .findByCharacterIdAndAbilityId(characterId, abilityId)
                .orElseGet(CharacterAbility::new);

        characterAbility.setCharacter(character);
        characterAbility.setAbility(ability);
        characterAbility.setValue(request.baseValue());

        validateAbilityBounds(characterAbility.getValue());

        Integer raceBonus = getRaceBonus(character, abilityId);
        Integer subraceBonus = getSubraceBonus(character, abilityId);

        validateFinalAbilityValue(characterAbility.getValue(), raceBonus, subraceBonus);

        CharacterAbility savedCharacterAbility = characterAbilityRepository.save(characterAbility);

        return CharacterAbilityMapper.toResponse(savedCharacterAbility, raceBonus, subraceBonus, dndRulesService);
    }

    @Override
    @Transactional
    public List<CharacterAbilityResponse> setCharacterAbilities(UUID characterId, SetCharacterAbilitiesRequest request) {
        UserCharacter character = getCurrentUserCharacter(characterId);
        validateCharacterRaceAndSubrace(character);
        validateUniqueAbilities(request.abilities());

        List<UUID> abilityIds = request.abilities().stream()
                .map(SetCharacterAbilityValueRequest::abilityId)
                .toList();
        Map<UUID, Ability> abilitiesById = abilityRepository.findAllById(abilityIds)
                .stream()
                .collect(Collectors.toMap(Ability::getId, Function.identity()));
        validateAllAbilitiesExist(abilityIds, abilitiesById);

        Map<UUID, CharacterAbility> currentAbilitiesById = characterAbilityRepository
                .findAllByCharacterId(characterId)
                .stream()
                .collect(Collectors.toMap(
                        characterAbility -> characterAbility.getAbility().getId(),
                        Function.identity()
                ));
        Map<UUID, Integer> raceBonusesByAbilityId = getRaceBonuses(character);
        Map<UUID, Integer> subraceBonusesByAbilityId = getSubraceBonuses(character);

        List<CharacterAbility> characterAbilities = request.abilities().stream()
                .map(abilityRequest -> buildCharacterAbility(
                        character,
                        abilitiesById.get(abilityRequest.abilityId()),
                        currentAbilitiesById.get(abilityRequest.abilityId()),
                        abilityRequest,
                        raceBonusesByAbilityId.getOrDefault(abilityRequest.abilityId(), 0),
                        subraceBonusesByAbilityId.getOrDefault(abilityRequest.abilityId(), 0)
                ))
                .toList();

        List<CharacterAbility> savedCharacterAbilities = characterAbilityRepository.saveAll(characterAbilities);

        return savedCharacterAbilities.stream()
                .map(characterAbility -> CharacterAbilityMapper.toResponse(
                        characterAbility,
                        raceBonusesByAbilityId.getOrDefault(characterAbility.getAbility().getId(), 0),
                        subraceBonusesByAbilityId.getOrDefault(characterAbility.getAbility().getId(), 0),
                        dndRulesService
                ))
                .toList();
    }

    private UserCharacter getCurrentUserCharacter(UUID characterId) {
        User user = authService.getCurrentUser();
        return characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Персонаж не найден"));
    }

    private Ability getAbility(UUID abilityId) {
        return abilityRepository.findById(abilityId)
                .orElseThrow(() -> new EntityNotFoundException("Характеристика не найдена"));
    }

    private CharacterAbility buildCharacterAbility(
            UserCharacter character,
            Ability ability,
            CharacterAbility currentCharacterAbility,
            SetCharacterAbilityValueRequest request,
            Integer raceBonus,
            Integer subraceBonus
    ) {
        CharacterAbility characterAbility = currentCharacterAbility == null
                ? new CharacterAbility()
                : currentCharacterAbility;

        characterAbility.setCharacter(character);
        characterAbility.setAbility(ability);
        characterAbility.setValue(request.baseValue());

        validateAbilityBounds(characterAbility.getValue());
        validateFinalAbilityValue(characterAbility.getValue(), raceBonus, subraceBonus);

        return characterAbility;
    }

    private void validateUniqueAbilities(List<SetCharacterAbilityValueRequest> abilityRequests) {
        Set<UUID> abilityIds = new HashSet<>();
        boolean hasDuplicate = abilityRequests.stream()
                .map(SetCharacterAbilityValueRequest::abilityId)
                .anyMatch(abilityId -> !abilityIds.add(abilityId));

        if (hasDuplicate) {
            throw new IllegalArgumentException("Характеристики не должны повторяться");
        }
    }

    private void validateAllAbilitiesExist(List<UUID> abilityIds, Map<UUID, Ability> abilitiesById) {
        abilityIds.stream()
                .filter(abilityId -> !abilitiesById.containsKey(abilityId))
                .findFirst()
                .ifPresent(abilityId -> {
                    throw new EntityNotFoundException("Характеристика не найдена");
                });
    }

    private void validateAbilityBounds(Integer value) {
        if (value < 1 || value > 20) {
            throw new IllegalArgumentException("baseValue должен быть в диапазоне 1..20");
        }
    }

    private void validateFinalAbilityValue(Integer baseValue, Integer raceBonus, Integer subraceBonus) {
        int finalValue = baseValue + raceBonus + subraceBonus;
        if (finalValue > 20) {
            throw new IllegalArgumentException("finalValue не должен быть больше 20");
        }
    }

    private void validateCharacterRaceAndSubrace(UserCharacter character) {
        if (character.getRace() == null) {
            throw new IllegalArgumentException("У персонажа не выбрана раса");
        }

        if (character.getSubrace() == null) {
            return;
        }

        if (character.getSubrace().getRace() == null) {
            throw new IllegalArgumentException("У подрасы не указана раса");
        }

        UUID characterRaceId = character.getRace().getId();
        UUID subraceRaceId = character.getSubrace().getRace().getId();

        if (!characterRaceId.equals(subraceRaceId)) {
            throw new IllegalArgumentException("Подраса не принадлежит выбранной расе персонажа");
        }
    }

    private Integer getRaceBonus(UserCharacter character, UUID abilityId) {
        return getRaceBonuses(character).getOrDefault(abilityId, 0);
    }

    private Integer getSubraceBonus(UserCharacter character, UUID abilityId) {
        return getSubraceBonuses(character).getOrDefault(abilityId, 0);
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
}
