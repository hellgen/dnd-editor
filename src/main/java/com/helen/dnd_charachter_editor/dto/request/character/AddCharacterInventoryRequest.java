package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddCharacterInventoryRequest(
        @NotNull
        UUID itemId,

        @NotNull
        @Min(1)
        Integer quantity,

        Boolean isEquipped,

        String customDescription
) {
}