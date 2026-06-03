package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `SubraceRepository` для доступа к данным.
 */
public interface SubraceRepository extends JpaRepository<Subrace, UUID> {
    /**
     * Находит данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<Subrace> findAllByRaceId(UUID raceId);

    /**
     * Находит данные для запрошенной операции.
     * @param id параметр, используемый при выполнении операции
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<Subrace> findByIdAndRaceId(UUID id, UUID raceId);
}
