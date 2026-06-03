package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `CharacterSavingThrowRepository` для доступа к данным.
 */
public interface CharacterSavingThrowRepository extends JpaRepository<CharacterSavingThrow, UUID> {
    /**
     * Находит данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<CharacterSavingThrow> findAllByCharacterId(UUID characterId);

    /**
     * Находит данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param abilityId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<CharacterSavingThrow> findByCharacterIdAndAbilityId(
            UUID characterId,
            UUID abilityId
    );
}
