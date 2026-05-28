package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubraceRepository extends JpaRepository<Subrace, UUID> {
    List<Subrace> findAllByRaceId(UUID raceId);

    Optional<Subrace> findByIdAndRaceId(UUID id, UUID raceId);
}