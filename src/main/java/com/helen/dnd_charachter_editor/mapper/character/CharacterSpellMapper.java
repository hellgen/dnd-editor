package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSpell;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;

/**
 * Маппер `CharacterSpellMapper` для преобразования данных между слоями приложения.
 */
public class CharacterSpellMapper {

    /**
     * Преобразует данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @param spell параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static CharacterSpell toEntity(UserCharacter character, Spell spell) {
        CharacterSpell characterSpell = new CharacterSpell();
        characterSpell.setCharacter(character);
        characterSpell.setSpell(spell);
        characterSpell.setIsPrepared(false);

        return characterSpell;
    }
}
