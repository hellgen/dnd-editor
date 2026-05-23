package com.helen.dnd_charachter_editor.dto.response.referencetable;

import java.util.UUID;

public record ItemResponse(
        UUID id,
        String name,
        String description
) {
}