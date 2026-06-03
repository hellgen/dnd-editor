package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing character repository data.
 */
public interface CharacterRepository extends JpaRepository<UserCharacter, UUID> {

    /**
     * Executes the exists by id operation.
     * @param id value used by this operation
     * @return result of the operation
     */
    boolean existsById(@NonNull UUID id);

    /**
     * Finds by id and user id.
     * @param id value used by this operation
     * @param userId value used by this operation
     * @return result of the operation
     */
    Optional<UserCharacter> findByIdAndUser_Id(UUID id, UUID userId);
}
