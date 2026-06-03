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

/**
 * Repository for accessing character ability repository data.
 */
public interface CharacterAbilityRepository extends JpaRepository<CharacterAbility, UUID> {

    /**
     * Finds by character id and ability id.
     * @param characterId value used by this operation
     * @param abilityId value used by this operation
     * @return result of the operation
     */
    Optional<CharacterAbility> findByCharacterIdAndAbilityId(
            UUID characterId,
            UUID abilityId
    );

    @Query("""
           select a
           from Ability a
           where a.id in :ids
           """)
    /**
     * Finds all by ids.
     * @param ids value used by this operation
     * @return result of the operation
     */
    List<Ability> findAllByIds(@Param("ids") Collection<UUID> ids);

    /**
     * Finds all by character id.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    List<CharacterAbility> findAllByCharacterId(UUID characterId);
}
