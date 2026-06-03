package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetypeFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing class archetype feature repository data.
 */
public interface ClassArchetypeFeatureRepository
        extends JpaRepository<ClassArchetypeFeature, UUID> {

    /**
     * Finds all by class archetype id and class archetype character class id.
     * @param classArchetypeId value used by this operation
     * @param classId value used by this operation
     * @return result of the operation
     */
    List<ClassArchetypeFeature> findAllByClassArchetypeIdAndClassArchetypeCharacterClassId(
            UUID classArchetypeId,
            UUID classId
    );

    /**
     * Finds all by class archetype id and class archetype character class id and level required less than equal order by level required asc.
     * @param classArchetypeId value used by this operation
     * @param classId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
     */
    List<ClassArchetypeFeature> findAllByClassArchetypeIdAndClassArchetypeCharacterClassIdAndLevelRequiredLessThanEqualOrderByLevelRequiredAsc(
            UUID classArchetypeId,
            UUID classId,
            Integer level
    );

    /**
     * Finds by id and class archetype id and class archetype character class id.
     * @param classArchetypeFeatureId value used by this operation
     * @param classArchetypeId value used by this operation
     * @param classId value used by this operation
     * @return result of the operation
     */
    Optional<ClassArchetypeFeature> findByIdAndClassArchetypeIdAndClassArchetypeCharacterClassId(
            UUID classArchetypeFeatureId,
            UUID classArchetypeId,
            UUID classId
    );
}
