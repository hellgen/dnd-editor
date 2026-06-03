package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository for accessing skill repository data.
 */
public interface SkillRepository extends JpaRepository<Skill, UUID> {
}
