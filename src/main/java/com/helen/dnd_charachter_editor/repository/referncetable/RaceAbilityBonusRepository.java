package com.helen.dnd_charachter_editor.repository.referncetable;

import com.helen.dnd_charachter_editor.entity.referencetable.RaceAbilityBonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RaceAbilityBonusRepository extends JpaRepository<RaceAbilityBonus, UUID> {

    Optional<RaceAbilityBonus> findByRaceIdAndAbilityId(
            UUID raceId,
            UUID abilityId
    );
}