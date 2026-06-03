package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data transfer object for add character inventory request.
 */
public record AddCharacterInventoryRequest(
        UUID itemId,

        @NotBlank
        String itemName,

        String itemDescription,

        @NotNull
        @Min(1)
        Integer quantity,

        Boolean isEquipped,

        String customDescription
) {
}
