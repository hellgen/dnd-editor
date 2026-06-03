package com.helen.dnd_charachter_editor.repository.reference.table;

import com.helen.dnd_charachter_editor.entity.reference.table.SubraceAbilityBonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing subrace ability bonus repository data.
 */
public interface SubraceAbilityBonusRepository extends JpaRepository<SubraceAbilityBonus, UUID> {

    /**
     * Finds by subrace id and ability id.
     * @param subraceId value used by this operation
     * @param abilityId value used by this operation
     * @return result of the operation
     */
    Optional<SubraceAbilityBonus> findBySubraceIdAndAbilityId(
            UUID subraceId,
            UUID abilityId
    );

    /**
     * Finds all by subrace id.
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    List<SubraceAbilityBonus> findAllBySubraceId(UUID subraceId);
}
