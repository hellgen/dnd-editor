package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий `CharacterClassRepository` для доступа к данным.
 */
public interface CharacterClassRepository extends JpaRepository<CharacterClass, UUID> {
}
