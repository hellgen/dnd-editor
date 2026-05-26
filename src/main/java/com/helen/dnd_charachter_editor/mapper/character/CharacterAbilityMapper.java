package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import org.springframework.stereotype.Component;

public class CharacterAbilityMapper {

    public static CharacterAbilityResponse toResponse(
            CharacterAbility characterAbility,
            Integer raceBonus,
            Integer subraceBonus
    ) {
        Integer baseValue = characterAbility.getValue();

        return new CharacterAbilityResponse(
                characterAbility.getId(),
                characterAbility.getCharacter().getId(),
                characterAbility.getAbility().getId(),
                characterAbility.getAbility().getCode(),
                characterAbility.getAbility().getName(),
                baseValue,
                raceBonus,
                subraceBonus,
                baseValue + raceBonus + subraceBonus
        );
    }
}