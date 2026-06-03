package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.RaceAbilityBonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `RaceAbilityBonusRepository` для доступа к данным.
 */
public interface RaceAbilityBonusRepository extends JpaRepository<RaceAbilityBonus, UUID> {

    /**
     * Находит данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param abilityId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<RaceAbilityBonus> findByRaceIdAndAbilityId(
            UUID raceId,
            UUID abilityId
    );

    /**
     * Находит данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<RaceAbilityBonus> findAllByRaceId(UUID raceId);
}
