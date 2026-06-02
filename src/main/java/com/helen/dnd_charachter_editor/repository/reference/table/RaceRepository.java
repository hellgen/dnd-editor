package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaceRepository extends JpaRepository<Race, UUID> {
}
