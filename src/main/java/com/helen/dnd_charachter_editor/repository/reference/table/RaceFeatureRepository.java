package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.RaceFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `RaceFeatureRepository` для доступа к данным.
 */
public interface RaceFeatureRepository extends JpaRepository<RaceFeature, UUID> {

    /**
     * Находит данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<RaceFeature> findAllByRaceId(UUID raceId);

    /**
     * Находит данные для запрошенной операции.
     * @param id параметр, используемый при выполнении операции
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<RaceFeature> findByIdAndRaceId(
            UUID id,
            UUID raceId
    );
}
