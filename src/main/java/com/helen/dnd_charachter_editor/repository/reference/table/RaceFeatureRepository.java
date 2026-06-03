package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.RaceFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing race feature repository data.
 */
public interface RaceFeatureRepository extends JpaRepository<RaceFeature, UUID> {

    /**
     * Finds all by race id.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    List<RaceFeature> findAllByRaceId(UUID raceId);

    /**
     * Finds by id and race id.
     * @param id value used by this operation
     * @param raceId value used by this operation
     * @return result of the operation
     */
    Optional<RaceFeature> findByIdAndRaceId(
            UUID id,
            UUID raceId
    );
}
