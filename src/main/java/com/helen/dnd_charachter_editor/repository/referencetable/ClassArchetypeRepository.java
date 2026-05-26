package com.helen.dnd_charachter_editor.repository.referencetable;

import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassArchetypeRepository extends JpaRepository<ClassArchetype, UUID> {

    List<ClassArchetype> findAllByCharacterClassId(UUID classId);

    Optional<ClassArchetype> findByIdAndCharacterClassId(
            UUID classArchetypeId,
            UUID classId
    );
}