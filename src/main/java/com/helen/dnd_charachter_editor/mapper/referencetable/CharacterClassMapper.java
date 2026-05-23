package com.helen.dnd_charachter_editor.mapper.referencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.CharacterClassResponse;
import com.helen.dnd_charachter_editor.entity.referencetable.CharacterClass;

public class CharacterClassMapper {
    public static CharacterClassResponse toCharacterClassResponse(CharacterClass characterClass) {
        return new CharacterClassResponse(
                characterClass.getId(),
                characterClass.getName(),
                characterClass.getHitDie(),
                characterClass.getPrimaryAbility()
        );
    }
}
