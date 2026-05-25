package com.helen.dnd_charachter_editor.repository.refernce.table;

import com.helen.dnd_charachter_editor.entity.reference.table.ClassFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassFeatureRepository extends JpaRepository<ClassFeature, UUID> {

    @Query(value = """
            SELECT *
            FROM class_features
            WHERE class_id = :classId
              AND level_required <= :level
            ORDER BY level_required ASC
            """, nativeQuery = true)
    List<ClassFeature> findAvailableClassFeaturesByClassIdAndLevel(
            @Param("classId") UUID classId,
            @Param("level") Integer level
    );

    @Query(value = """
            SELECT *
            FROM class_features
            WHERE class_feature_id = :classFeatureId
              AND class_id = :classId
              AND level_required <= :level
            """, nativeQuery = true)
    Optional<ClassFeature> findAvailableClassFeatureByIdAndClassIdAndLevel(
            @Param("classFeatureId") UUID classFeatureId,
            @Param("classId") UUID classId,
            @Param("level") Integer level
    );
}