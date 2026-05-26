package com.helen.dnd_charachter_editor.repository.referencetable;

import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetypeFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassArchetypeFeatureRepository
        extends JpaRepository<ClassArchetypeFeature, UUID> {

    List<ClassArchetypeFeature> findAllByClassArchetypeIdAndClassArchetypeCharacterClassId(
            UUID classArchetypeId,
            UUID classId
    );

    Optional<ClassArchetypeFeature> findByIdAndClassArchetypeIdAndClassArchetypeCharacterClassId(
            UUID classArchetypeFeatureId,
            UUID classArchetypeId,
            UUID classId
    );
}