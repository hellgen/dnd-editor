package com.helen.dnd_charachter_editor.dto.response.character;

import java.util.UUID;

/**
 * Объект передачи данных `CharacterInventoryResponse`.
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
