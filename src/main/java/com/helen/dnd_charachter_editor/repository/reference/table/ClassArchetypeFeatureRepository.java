package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetypeFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `ClassArchetypeFeatureRepository` для доступа к данным.
 */
public interface ClassArchetypeFeatureRepository
        extends JpaRepository<ClassArchetypeFeature, UUID> {

    /**
     * Находит данные для запрошенной операции.
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<ClassArchetypeFeature> findAllByClassArchetypeIdAndClassArchetypeCharacterClassId(
            UUID classArchetypeId,
            UUID classId
    );

    /**
     * Находит данные для запрошенной операции.
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param classId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<ClassArchetypeFeature> findAllByClassArchetypeIdAndClassArchetypeCharacterClassIdAndLevelRequiredLessThanEqualOrderByLevelRequiredAsc(
            UUID classArchetypeId,
            UUID classId,
            Integer level
    );

    /**
     * Находит данные для запрошенной операции.
     * @param classArchetypeFeatureId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<ClassArchetypeFeature> findByIdAndClassArchetypeIdAndClassArchetypeCharacterClassId(
            UUID classArchetypeFeatureId,
            UUID classArchetypeId,
            UUID classId
    );
}
