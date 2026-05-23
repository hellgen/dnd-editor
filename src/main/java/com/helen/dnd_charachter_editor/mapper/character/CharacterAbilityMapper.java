package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import org.springframework.stereotype.Component;

@Component
public class CharacterAbilityMapper {

    public CharacterAbilityResponse toResponse(
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