package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;

import java.util.List;
import java.util.UUID;

/**
 * Service contract for spell reference operations.
 */
public interface SpellService {

    /**
     * Returns all spells.
     * @return result of the operation
     */
    List<SpellResponse> getAllSpells();

    /**
     * Returns one spell response.
     * @param spellId value used by this operation
     * @return result of the operation
     */
    SpellResponse getSpellResponse(UUID spellId);

    /**
     * Returns one spell entity.
     * @param spellId value used by this operation
     * @return result of the operation
     */
    Spell getSpell(UUID spellId);

    /**
     * Returns spells available for class.
     * @param classId value used by this operation
     * @return result of the operation
     */
    List<SpellResponse> getSpellsByClassId(UUID classId);

    /**
     * Checks that spell is available for character class.
     * @param characterClass value used by this operation
     * @param spell value used by this operation
     * @return result of the operation
     */
    boolean isSpellAvailableForClass(CharacterClass characterClass, Spell spell);
}
