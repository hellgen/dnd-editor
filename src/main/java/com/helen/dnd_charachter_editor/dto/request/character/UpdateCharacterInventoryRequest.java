package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.Min;

public record UpdateCharacterInventoryRequest(
        @Min(1)
        Integer quantity,

        Boolean isEquipped,

        String customDescription
) {
}