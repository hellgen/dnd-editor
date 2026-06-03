package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.Spell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository for accessing spell repository data.
 */
public interface SpellRepository extends JpaRepository<Spell, UUID> {
}
