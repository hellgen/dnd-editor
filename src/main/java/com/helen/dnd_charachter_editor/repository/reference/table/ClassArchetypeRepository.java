package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `ClassArchetypeRepository` для доступа к данным.
 */
public interface ClassArchetypeRepository extends JpaRepository<ClassArchetype, UUID> {

    /**
     * Находит данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<ClassArchetype> findAllByCharacterClassId(UUID classId);

    /**
     * Находит данные для запрошенной операции.
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<ClassArchetype> findByIdAndCharacterClassId(
            UUID classArchetypeId,
            UUID classId
    );
}
