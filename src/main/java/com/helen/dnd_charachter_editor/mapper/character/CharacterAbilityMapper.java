package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.service.character.DndRulesService;

/**
 * Маппер `CharacterAbilityMapper` для преобразования данных между слоями приложения.
 */
public class CharacterAbilityMapper {


    /**
     * Преобразует данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @param ability параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static CharacterAbility toEntity(UserCharacter character, Ability ability) {
        CharacterAbility characterAbility = new CharacterAbility();
        characterAbility.setCharacter(character);
        characterAbility.setAbility(ability);
        characterAbility.setValue(0);

        return characterAbility;
    }

    /**
     * Преобразует данные для запрошенной операции.
     * @param characterAbility параметр, используемый при выполнении операции
     * @param raceBonus параметр, используемый при выполнении операции
     * @param subraceBonus параметр, используемый при выполнении операции
     * @param dndRulesService параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static CharacterAbilityResponse toResponse(
            CharacterAbility characterAbility,
            Integer raceBonus,
            Integer subraceBonus,
            DndRulesService dndRulesService
    ) {
        Integer baseValue = characterAbility.getValue();
        Integer totalValue = baseValue + raceBonus + subraceBonus;
        Integer modifier = dndRulesService.calculateAbilityModifier(totalValue);

        return new CharacterAbilityResponse(
                characterAbility.getId(),
                characterAbility.getCharacter().getId(),
                characterAbility.getAbility().getId(),
                characterAbility.getAbility().getCode(),
                characterAbility.getAbility().getName(),
                baseValue,
                raceBonus,
                subraceBonus,
                totalValue,
                modifier
        );
    }
}
