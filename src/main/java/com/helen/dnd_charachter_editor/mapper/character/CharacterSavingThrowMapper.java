package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;

/**
 * Mapper that converts character saving throw mapper values between layers.
 */
public class CharacterSavingThrowMapper {

    /**
     * Converts entity.
     * @param character value used by this operation
     * @param ability value used by this operation
     * @return result of the operation
     */
    public static CharacterSavingThrow toEntity(UserCharacter character, Ability ability) {
        CharacterSavingThrow characterSavingThrow = new CharacterSavingThrow();
        characterSavingThrow.setCharacter(character);
        characterSavingThrow.setAbility(ability);
        characterSavingThrow.setProficiencyLevel(0);

        return characterSavingThrow;
    }
}
