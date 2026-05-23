package com.helen.dnd_charachter_editor.repository.referncetable;

import com.helen.dnd_charachter_editor.entity.referencetable.ClassArchetype;
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