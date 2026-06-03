package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Data transfer object for class archetype response.
 */
public record ClassArchetypeResponse(
        UUID id,
        UUID classId,
        String name,
        String description
) {
}
