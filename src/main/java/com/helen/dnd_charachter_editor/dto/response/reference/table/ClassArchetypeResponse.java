package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Объект передачи данных `ClassArchetypeResponse`.
 */
public record ClassArchetypeResponse(
        UUID id,
        UUID classId,
        String name,
        String description
) {
}
