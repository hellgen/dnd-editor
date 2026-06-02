package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.SubraceFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubraceFeatureRepository extends JpaRepository<SubraceFeature, UUID> {

    List<SubraceFeature> findAllBySubraceId(UUID subraceId);

    Optional<SubraceFeature> findByIdAndSubraceId(
            UUID id,
            UUID subraceId
    );
}
