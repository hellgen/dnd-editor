package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.RaceAbilityBonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing race ability bonus repository data.
 */
public interface RaceAbilityBonusRepository extends JpaRepository<RaceAbilityBonus, UUID> {

    /**
     * Finds by race id and ability id.
     * @param raceId value used by this operation
     * @param abilityId value used by this operation
     * @return result of the operation
     */
    Optional<RaceAbilityBonus> findByRaceIdAndAbilityId(
            UUID raceId,
            UUID abilityId
    );

    /**
     * Finds all by race id.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    List<RaceAbilityBonus> findAllByRaceId(UUID raceId);
}
