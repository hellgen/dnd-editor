package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing character skill repository data.
 */
public interface CharacterSkillRepository extends JpaRepository<CharacterSkill, UUID> {
    /**
     * Finds all by character id.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    List<CharacterSkill> findAllByCharacterId(UUID characterId);

    /**
     * Finds by character id and skill id.
     * @param characterId value used by this operation
     * @param skillId value used by this operation
     * @return result of the operation
     */
    Optional<CharacterSkill> findByCharacterIdAndSkillId(
            UUID characterId,
            UUID skillId
    );
}
