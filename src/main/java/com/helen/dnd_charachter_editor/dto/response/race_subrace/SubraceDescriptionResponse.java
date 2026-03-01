package com.helen.dnd_charachter_editor.dto.response.race_subrace;

import java.util.UUID;

public record SubraceDescriptionResponse(
    UUID id,
    String name,
    String description
) {
}
