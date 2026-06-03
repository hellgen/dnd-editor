package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.SubraceFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `SubraceFeatureRepository` для доступа к данным.
 */
public interface SubraceFeatureRepository extends JpaRepository<SubraceFeature, UUID> {

    /**
     * Находит данные для запрошенной операции.
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<SubraceFeature> findAllBySubraceId(UUID subraceId);

    /**
     * Находит данные для запрошенной операции.
     * @param id параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<SubraceFeature> findByIdAndSubraceId(
            UUID id,
            UUID subraceId
    );
}
