package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Объект передачи данных `UpdateCharacterInventoryRequest`.
 */
public record UpdateCharacterInventoryRequest(
        @NotBlank
        String itemName,

        String newItemName,

        String itemDescription,

        @Min(1)
        Integer quantity,

        Boolean isEquipped,

        String customDescription
) {
}
