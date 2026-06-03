package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Data transfer object for subrace description response.
 */
public record SubraceDescriptionResponse(
    UUID id,
    String name,
    String description
) {
}
