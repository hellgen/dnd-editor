package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.Min;

public record WalletUpdateRequest(

        @Min(0)
        Integer copper,

        @Min(0)
        Integer silver,

        @Min(0)
        Integer electrum,

        @Min(0)
        Integer gold,

        @Min(0)
        Integer platinum
) {
}