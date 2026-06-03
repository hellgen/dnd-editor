package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.CharacterClassResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;

/**
 * Mapper that converts character class mapper values between layers.
 */
public class CharacterClassMapper {
    /**
     * Converts character class response.
     * @param characterClass value used by this operation
     * @return result of the operation
     */
    public static CharacterClassResponse toCharacterClassResponse(CharacterClass characterClass) {
        return new CharacterClassResponse(
                characterClass.getId(),
                characterClass.getClassName(),
                characterClass.getClassDescription(),
                characterClass.getIsSpellcaster(),
                characterClass.getSpellcastingStartLevel()
        );
    }
}
