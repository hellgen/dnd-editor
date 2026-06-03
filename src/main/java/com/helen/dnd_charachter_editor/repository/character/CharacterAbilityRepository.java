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
 * Репозиторий `CharacterAbilityRepository` для доступа к данным.
 */
public interface CharacterAbilityRepository extends JpaRepository<CharacterAbility, UUID> {

    /**
     * Находит данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param abilityId параметр, используемый при выполнении операции
     * @return результат выполнения операции
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
     * Находит данные для запрошенной операции.
     * @param ids параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<Ability> findAllByIds(@Param("ids") Collection<UUID> ids);

    /**
     * Находит данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<CharacterAbility> findAllByCharacterId(UUID characterId);
}
