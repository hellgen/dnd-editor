package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;

/**
 * Маппер `CharacterSavingThrowMapper` для преобразования данных между слоями приложения.
 */
public class CharacterSavingThrowMapper {

    /**
     * Преобразует данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @param ability параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static CharacterSavingThrow toEntity(UserCharacter character, Ability ability) {
        CharacterSavingThrow characterSavingThrow = new CharacterSavingThrow();
        characterSavingThrow.setCharacter(character);
        characterSavingThrow.setAbility(ability);
        characterSavingThrow.setProficiencyLevel(0);

        return characterSavingThrow;
    }
}
