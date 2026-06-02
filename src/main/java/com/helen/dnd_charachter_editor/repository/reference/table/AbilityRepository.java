package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AbilityRepository extends JpaRepository<Ability, UUID> {
}