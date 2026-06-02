package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.ClassFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassFeatureRepository extends JpaRepository<ClassFeature, UUID> {

    List<ClassFeature> findAllByCharacterClassIdOrderByLevelRequiredAsc(UUID classId);

    Optional<ClassFeature> findByIdAndCharacterClassId(
            UUID id,
            UUID classId
    );
}
