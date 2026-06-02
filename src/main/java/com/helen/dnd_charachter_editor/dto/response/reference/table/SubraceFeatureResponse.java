package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

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
