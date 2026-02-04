package com.helen.dnd_charachter_editor.repository;

import com.helen.dnd_charachter_editor.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaceRepository extends JpaRepository<UUID, Race> {

}
