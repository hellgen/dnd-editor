package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.Min;

/**
 * Data transfer object for update character inventory request.
 */
public record UpdateCharacterInventoryRequest(
        @Min(1)
        Integer quantity,

        Boolean isEquipped,

        String customDescription
) {
}
