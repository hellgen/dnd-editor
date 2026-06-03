package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Объект передачи данных `RaceFeatureResponse`.
 */
public record RaceFeatureResponse(
        UUID id,
        UUID raceId,
        String raceName,
        String name,
        String description
) {
}
