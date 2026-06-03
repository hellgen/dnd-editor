package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

public record SubraceResponse(
        UUID id,
        UUID raceId,
        String raceName,
        String name,
        String description
) {
}
