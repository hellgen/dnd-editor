package com.helen.dnd_charachter_editor.repository.referencetable;

import com.helen.dnd_charachter_editor.entity.reference.table.RaceAbilityBonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RaceAbilityBonusRepository extends JpaRepository<RaceAbilityBonus, UUID> {

    Optional<RaceAbilityBonus> findByRaceIdAndAbilityId(
            UUID raceId,
            UUID abilityId
    );
}