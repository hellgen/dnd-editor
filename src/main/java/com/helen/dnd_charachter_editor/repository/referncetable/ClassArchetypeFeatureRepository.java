package com.helen.dnd_charachter_editor.repository.referncetable;

import com.helen.dnd_charachter_editor.entity.referencetable.ClassArchetypeFeature;
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