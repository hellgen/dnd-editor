package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Data transfer object for character class response.
 */
public record CharacterClassResponse(
        UUID id,
        String className,
        String classDescription,
        Boolean isSpellcaster,
        Integer spellcastingStartLevel
) {
}
