package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSpell;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;

public class CharacterSpellMapper {

    public static CharacterSpell toEntity(UserCharacter character, Spell spell) {
        CharacterSpell characterSpell = new CharacterSpell();
        characterSpell.setCharacter(character);
        characterSpell.setSpell(spell);
        characterSpell.setIsPrepared(false);

        return characterSpell;
    }
}
