package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.service.character.DndRulesService;

/**
 * Mapper that converts character ability mapper values between layers.
 */
public class CharacterAbilityMapper {


    /**
     * Converts entity.
     * @param character value used by this operation
     * @param ability value used by this operation
     * @return result of the operation
     */
    public static CharacterAbility toEntity(UserCharacter character, Ability ability) {
        CharacterAbility characterAbility = new CharacterAbility();
        characterAbility.setCharacter(character);
        characterAbility.setAbility(ability);
        characterAbility.setValue(0);

        return characterAbility;
    }

    /**
     * Converts response.
     * @param characterAbility value used by this operation
     * @param raceBonus value used by this operation
     * @param subraceBonus value used by this operation
     * @param dndRulesService value used by this operation
     * @return result of the operation
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
