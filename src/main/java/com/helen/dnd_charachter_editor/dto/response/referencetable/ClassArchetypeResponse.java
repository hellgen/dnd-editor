package com.helen.dnd_charachter_editor.dto.response.referencetable;

import java.util.UUID;

public record ClassArchetypeResponse(
        UUID id,
        UUID classId,
        String name,
        String description
) {
}
