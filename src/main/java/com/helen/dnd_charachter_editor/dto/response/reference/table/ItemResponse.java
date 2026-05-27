package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

public record ItemResponse(
        UUID id,
        String name,
        String description
) {
}