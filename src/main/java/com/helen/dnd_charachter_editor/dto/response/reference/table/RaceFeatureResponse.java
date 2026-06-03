package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Data transfer object for race feature response.
 */
public record RaceFeatureResponse(
        UUID id,
        UUID raceId,
        String raceName,
        String name,
        String description
) {
}
