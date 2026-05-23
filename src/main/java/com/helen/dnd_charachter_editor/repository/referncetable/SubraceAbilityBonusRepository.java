package com.helen.dnd_charachter_editor.repository.referncetable;

import com.helen.dnd_charachter_editor.entity.referencetable.SubraceAbilityBonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubraceAbilityBonusRepository extends JpaRepository<SubraceAbilityBonus, UUID> {

    Optional<SubraceAbilityBonus> findBySubraceIdAndAbilityId(
            UUID subraceId,
            UUID abilityId
    );
}