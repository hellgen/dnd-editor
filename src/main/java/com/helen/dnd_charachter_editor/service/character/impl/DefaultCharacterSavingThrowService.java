package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSavingThrowRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSavingThrowResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceAbilityBonus;
import com.helen.dnd_charachter_editor.entity.reference.table.SubraceAbilityBonus;
import com.helen.dnd_charachter_editor.mapper.character.CharacterSavingThrowMapper;
import com.helen.dnd_charachter_editor.repository.character.CharacterAbilityRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterSavingThrowRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.AbilityRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.RaceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.SubraceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.service.auth.AuthService;
import com.helen.dnd_charachter_editor.service.character.CharacterSavingThrowService;
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
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Реализация сервиса `DefaultCharacterSavingThrowService`.
 */
@Service
@RequiredArgsConstructor
public class DefaultCharacterSavingThrowService implements CharacterSavingThrowService {

    private final AuthService authService;
    private final CharacterRepository characterRepository;
    private final CharacterSavingThrowRepository characterSavingThrowRepository;
    private final CharacterAbilityRepository characterAbilityRepository;
    private final AbilityRepository abilityRepository;
    private final RaceAbilityBonusRepository raceAbilityBonusRepository;
    private final SubraceAbilityBonusRepository subraceAbilityBonusRepository;
    private final DndRulesService dndRulesService;

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional(readOnly = true)
    public List<CharacterSavingThrowResponse> getCharacterSavingThrows(UUID characterId) {
        UserCharacter character = getCurrentUserCharacter(characterId);
        SavingThrowCalculationContext context = buildCalculationContext(character);

        return characterSavingThrowRepository.findAllByCharacterId(characterId)
                .stream()
                .sorted(Comparator.comparing(savingThrow -> savingThrow.getAbility().getName()))
                .map(savingThrow -> toResponse(savingThrow, context))
                .toList();
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param abilityId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public CharacterSavingThrowResponse updateCharacterSavingThrow(
            UUID characterId,
            UUID abilityId,
            SetCharacterSavingThrowRequest request
    ) {
        UserCharacter character = getCurrentUserCharacter(characterId);
        Ability ability = abilityRepository.findById(abilityId)
                .orElseThrow(() -> new EntityNotFoundException("Характеристика не найдена"));

        validateProficiencyLevel(request.proficiencyLevel());

        CharacterSavingThrow savingThrow = characterSavingThrowRepository
                .findByCharacterIdAndAbilityId(characterId, abilityId)
                .orElseGet(() -> CharacterSavingThrowMapper.toEntity(character, ability));

        savingThrow.setCharacter(character);
        savingThrow.setAbility(ability);
        savingThrow.setProficiencyLevel(request.proficiencyLevel());

        CharacterSavingThrow savedSavingThrow = characterSavingThrowRepository.save(savingThrow);
        SavingThrowCalculationContext context = buildCalculationContext(character);

        return toResponse(savedSavingThrow, context);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private UserCharacter getCurrentUserCharacter(UUID characterId) {
        User user = authService.getCurrentUser();
        return characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Персонаж не найден"));
    }

    /**
     * Проверяет корректность данных для запрошенной операции.
     * @param proficiencyLevel параметр, используемый при выполнении операции
     */
    private void validateProficiencyLevel(Integer proficiencyLevel) {
        if (proficiencyLevel == null || proficiencyLevel < 0 || proficiencyLevel > 1) {
            throw new IllegalArgumentException("proficiencyLevel должен быть в диапазоне 0..1");
        }
    }

    /**
     * Преобразует данные для запрошенной операции.
     * @param savingThrow параметр, используемый при выполнении операции
     * @param context параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterSavingThrowResponse toResponse(
            CharacterSavingThrow savingThrow,
            SavingThrowCalculationContext context
    ) {
        Ability ability = savingThrow.getAbility();
        Integer abilityModifier = calculateAbilityModifier(ability, context);
        Integer proficiencyBonus = context.proficiencyBonus();
        Integer proficiencyLevel = savingThrow.getProficiencyLevel();
        Integer totalModifier = abilityModifier + proficiencyBonus * proficiencyLevel;

        return new CharacterSavingThrowResponse(
                savingThrow.getId(),
                savingThrow.getCharacter().getId(),
                ability.getId(),
                ability.getCode(),
                ability.getName(),
                abilityModifier,
                proficiencyLevel,
                proficiencyBonus,
                totalModifier
        );
    }

    /**
     * Вычисляет значение для запрошенной операции.
     * @param ability параметр, используемый при выполнении операции
     * @param context параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Integer calculateAbilityModifier(
            Ability ability,
            SavingThrowCalculationContext context
    ) {
        CharacterAbility characterAbility = context.characterAbilitiesByAbilityId().get(ability.getId());
        Integer baseValue = characterAbility != null ? characterAbility.getValue() : 0;
        Integer finalValue = baseValue
                + context.raceBonusesByAbilityId().getOrDefault(ability.getId(), 0)
                + context.subraceBonusesByAbilityId().getOrDefault(ability.getId(), 0);

        return dndRulesService.calculateAbilityModifier(finalValue);
    }

    /**
     * Формирует данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private SavingThrowCalculationContext buildCalculationContext(UserCharacter character) {
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

        return new SavingThrowCalculationContext(
                characterAbilitiesByAbilityId,
                raceBonusesByAbilityId,
                subraceBonusesByAbilityId,
                proficiencyBonus
        );
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Map<UUID, Integer> getRaceBonuses(UserCharacter character) {
        UUID raceId = character.getRace().getId();

        return raceAbilityBonusRepository.findAllByRaceId(raceId)
                .stream()
                .collect(Collectors.toMap(
                        raceAbilityBonus -> raceAbilityBonus.getAbility().getId(),
                        RaceAbilityBonus::getBonusValue
                ));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
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

    /**
     * Реализация сервиса `SavingThrowCalculationContext`.
     */
    private record SavingThrowCalculationContext(
            Map<UUID, CharacterAbility> characterAbilitiesByAbilityId,
            Map<UUID, Integer> raceBonusesByAbilityId,
            Map<UUID, Integer> subraceBonusesByAbilityId,
            Integer proficiencyBonus
    ) {
    }
}
