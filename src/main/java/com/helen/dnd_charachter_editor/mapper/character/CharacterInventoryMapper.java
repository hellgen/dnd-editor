package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.dto.response.character.CharacterInventoryResponse;
import com.helen.dnd_charachter_editor.entity.character.CharacterInventory;
import org.springframework.stereotype.Component;

@Component
public class CharacterInventoryMapper {

    public CharacterInventoryResponse toResponse(CharacterInventory inventory) {
        return new CharacterInventoryResponse(
                inventory.getId(),
                inventory.getCharacter().getId(),
                inventory.getItem().getId(),
                inventory.getItem().getName(),
                inventory.getItem().getDescription(),
                inventory.getQuantity(),
                inventory.getIsEquipped(),
                inventory.getCustomDescription()
        );
    }
}