package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.SubraceAbilityBonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `SubraceAbilityBonusRepository` для доступа к данным.
 */
public interface SubraceAbilityBonusRepository extends JpaRepository<SubraceAbilityBonus, UUID> {

    /**
     * Находит данные для запрошенной операции.
     * @param subraceId параметр, используемый при выполнении операции
     * @param abilityId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<SubraceAbilityBonus> findBySubraceIdAndAbilityId(
            UUID subraceId,
            UUID abilityId
    );

    /**
     * Находит данные для запрошенной операции.
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<SubraceAbilityBonus> findAllBySubraceId(UUID subraceId);
}
