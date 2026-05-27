package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;

public class CharacterSavingThrowMapper {

    public static CharacterSavingThrow toEntity(UserCharacter character, Ability ability) {
        CharacterSavingThrow characterSavingThrow = new CharacterSavingThrow();
        characterSavingThrow.setCharacter(character);
        characterSavingThrow.setAbility(ability);
        characterSavingThrow.setProficiencyLevel(0);

        return characterSavingThrow;
    }
}
