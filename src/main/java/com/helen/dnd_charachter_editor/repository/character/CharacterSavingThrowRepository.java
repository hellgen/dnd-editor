package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing character saving throw repository data.
 */
public interface CharacterSavingThrowRepository extends JpaRepository<CharacterSavingThrow, UUID> {
    /**
     * Finds all by character id.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    List<CharacterSavingThrow> findAllByCharacterId(UUID characterId);

    /**
     * Finds by character id and ability id.
     * @param characterId value used by this operation
     * @param abilityId value used by this operation
     * @return result of the operation
     */
    Optional<CharacterSavingThrow> findByCharacterIdAndAbilityId(
            UUID characterId,
            UUID abilityId
    );
}
