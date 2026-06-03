package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Объект передачи данных `RaceDescriptionResponse`.
 */
public record RaceDescriptionResponse(
        UUID id,
        int age,
        int height,
        int speed,
        String languages,
        String description
) {
}
