package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.ClassFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing class feature repository data.
 */
public interface ClassFeatureRepository extends JpaRepository<ClassFeature, UUID> {

    /**
     * Finds all by character class id order by level required asc.
     * @param classId value used by this operation
     * @return result of the operation
     */
    List<ClassFeature> findAllByCharacterClassIdOrderByLevelRequiredAsc(UUID classId);

    /**
     * Finds all by character class id and level required less than equal order by level required asc.
     * @param classId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
     */
    List<ClassFeature> findAllByCharacterClassIdAndLevelRequiredLessThanEqualOrderByLevelRequiredAsc(
            UUID classId,
            Integer level
    );

    /**
     * Finds by id and character class id.
     * @param id value used by this operation
     * @param classId value used by this operation
     * @return result of the operation
     */
    Optional<ClassFeature> findByIdAndCharacterClassId(
            UUID id,
            UUID classId
    );
}
