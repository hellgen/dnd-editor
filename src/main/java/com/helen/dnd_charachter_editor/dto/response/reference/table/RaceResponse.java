package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Объект передачи данных `RaceResponse`.
 */
public record RaceResponse(
        UUID id,
        String name,
        Integer age,
        Integer height,
        Integer speed,
        String languages,
        String description
) {
}
