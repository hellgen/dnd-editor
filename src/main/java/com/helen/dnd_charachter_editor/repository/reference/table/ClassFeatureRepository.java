package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.ClassFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `ClassFeatureRepository` для доступа к данным.
 */
public interface ClassFeatureRepository extends JpaRepository<ClassFeature, UUID> {

    /**
     * Находит данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<ClassFeature> findAllByCharacterClassIdOrderByLevelRequiredAsc(UUID classId);

    /**
     * Находит данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<ClassFeature> findAllByCharacterClassIdAndLevelRequiredLessThanEqualOrderByLevelRequiredAsc(
            UUID classId,
            Integer level
    );

    /**
     * Находит данные для запрошенной операции.
     * @param id параметр, используемый при выполнении операции
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<ClassFeature> findByIdAndCharacterClassId(
            UUID id,
            UUID classId
    );
}
