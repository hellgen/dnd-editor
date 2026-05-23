package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CharacterInventoryRepository extends JpaRepository<CharacterInventory, UUID> {

    List<CharacterInventory> findAllByCharacterId(UUID characterId);

    Optional<CharacterInventory> findByIdAndCharacterId(
            UUID inventoryId,
            UUID characterId
    );

    Optional<CharacterInventory> findByCharacterIdAndItemId(
            UUID characterId,
            UUID itemId
    );
}