package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.CharacterClassResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import lombok.NoArgsConstructor;

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
