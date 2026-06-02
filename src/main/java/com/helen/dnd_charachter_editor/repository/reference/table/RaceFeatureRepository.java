package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.RaceFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RaceFeatureRepository extends JpaRepository<RaceFeature, UUID> {

    List<RaceFeature> findAllByRaceId(UUID raceId);

    Optional<RaceFeature> findByIdAndRaceId(
            UUID id,
            UUID raceId
    );
}
