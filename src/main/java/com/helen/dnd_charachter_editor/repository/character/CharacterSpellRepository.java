package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSpell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for accessing character spell repository data.
 */
public interface CharacterSpellRepository extends JpaRepository<CharacterSpell, UUID> {
    /**
     * Finds all by character id.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    List<CharacterSpell> findAllByCharacterId(UUID characterId);
}
