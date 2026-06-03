package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.SubraceFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing subrace feature repository data.
 */
public interface SubraceFeatureRepository extends JpaRepository<SubraceFeature, UUID> {

    /**
     * Finds all by subrace id.
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    List<SubraceFeature> findAllBySubraceId(UUID subraceId);

    /**
     * Finds by id and subrace id.
     * @param id value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    Optional<SubraceFeature> findByIdAndSubraceId(
            UUID id,
            UUID subraceId
    );
}
