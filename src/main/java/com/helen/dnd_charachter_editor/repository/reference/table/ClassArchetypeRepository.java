package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing class archetype repository data.
 */
public interface ClassArchetypeRepository extends JpaRepository<ClassArchetype, UUID> {

    /**
     * Finds all by character class id.
     * @param classId value used by this operation
     * @return result of the operation
     */
    List<ClassArchetype> findAllByCharacterClassId(UUID classId);

    /**
     * Finds by id and character class id.
     * @param classArchetypeId value used by this operation
     * @param classId value used by this operation
     * @return result of the operation
     */
    Optional<ClassArchetype> findByIdAndCharacterClassId(
            UUID classArchetypeId,
            UUID classId
    );
}
