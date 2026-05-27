package com.helen.dnd_charachter_editor.dto.response.character;

import lombok.Builder;

import java.util.UUID;

@Builder
public record WalletResponse(
        UUID characterWalletId,
        UUID characterId,
        Integer copper,
        Integer silver,
        Integer electrum,
        Integer gold,
        Integer platinum
) {
}