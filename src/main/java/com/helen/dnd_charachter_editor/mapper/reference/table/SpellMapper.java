package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;

/**
 * Mapper that converts spell values between layers.
 */
public class SpellMapper {

    /**
     * Converts spell entity to spell response.
     * @param spell value used by this operation
     * @return result of the operation
     */
    public static SpellResponse toSpellResponse(Spell spell) {
        return new SpellResponse(
                spell.getId(),
                spell.getSpellName(),
                spell.getSpellLevel(),
                spell.getSpellSchool(),
                spell.getCastingTime(),
                spell.getSpellRange(),
                spell.getComponents(),
                spell.getDuration(),
                spell.getSpellDescription()
        );
    }
}
