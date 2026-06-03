package com.helen.dnd_charachter_editor.dto.response.character;

import java.util.UUID;

/**
 * Data transfer object for character inventory response.
 */
public record CharacterInventoryResponse(
        UUID id,
        UUID characterId,
        UUID itemId,
        String itemName,
        String itemDescription,
        Integer quantity,
        Boolean isEquipped,
        String customDescription
) {
}
