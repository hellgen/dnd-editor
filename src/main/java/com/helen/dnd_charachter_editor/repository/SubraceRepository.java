package com.helen.dnd_charachter_editor.repository;

import com.helen.dnd_charachter_editor.entity.Subrace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubraceRepository extends JpaRepository<Subrace, UUID> {
    List<Subrace> findByRaceId(UUID raceId);
}
