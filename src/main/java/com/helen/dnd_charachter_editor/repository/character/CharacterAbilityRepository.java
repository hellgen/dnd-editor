package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CharacterAbilityRepository extends JpaRepository<CharacterAbility, UUID> {

    Optional<CharacterAbility> findByCharacterIdAndAbilityId(
            UUID characterId,
            UUID abilityId
    );

    @Query("""
           select a
           from Ability a
           where a.id in :ids
           """)
    List<Ability> findAllByIds(@Param("ids") Collection<UUID> ids);

    List<CharacterAbility> findAllByCharacterId(UUID characterId);
}
