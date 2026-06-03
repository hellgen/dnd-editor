package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSpell;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;

/**
 * Mapper that converts character spell mapper values between layers.
 */
public class CharacterSpellMapper {

    /**
     * Converts entity.
     * @param character value used by this operation
     * @param spell value used by this operation
     * @return result of the operation
     */
    public static CharacterSpell toEntity(UserCharacter character, Spell spell) {
        CharacterSpell characterSpell = new CharacterSpell();
        characterSpell.setCharacter(character);
        characterSpell.setSpell(spell);
        characterSpell.setIsPrepared(false);

        return characterSpell;
    }
}
