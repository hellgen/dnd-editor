package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Data transfer object for subrace feature response.
 */
public record SubraceFeatureResponse(
        UUID id,
        UUID raceId,
        String raceName,
        UUID subraceId,
        String subraceName,
        String name,
        String description
) {
}
