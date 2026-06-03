package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Data transfer object for spell response.
 */
public record SpellResponse(
        UUID id,
        String spellName,
        Integer spellLevel,
        String spellSchool,
        String castingTime,
        String spellRange,
        String components,
        String duration,
        String spellDescription
) {
}
