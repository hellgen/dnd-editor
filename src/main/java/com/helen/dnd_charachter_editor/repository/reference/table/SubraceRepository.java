package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing subrace repository data.
 */
public interface SubraceRepository extends JpaRepository<Subrace, UUID> {
    /**
     * Finds all by race id.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    List<Subrace> findAllByRaceId(UUID raceId);

    /**
     * Finds by id and race id.
     * @param id value used by this operation
     * @param raceId value used by this operation
     * @return result of the operation
     */
    Optional<Subrace> findByIdAndRaceId(UUID id, UUID raceId);
}
