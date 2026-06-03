package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSpell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `CharacterSpellRepository` для доступа к данным.
 */
public interface CharacterSpellRepository extends JpaRepository<CharacterSpell, UUID> {
    /**
     * Находит данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<CharacterSpell> findAllByCharacterId(UUID characterId);

    /**
     * Находит данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param spellId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<CharacterSpell> findByCharacterIdAndSpellId(UUID characterId, UUID spellId);

    /**
     * Проверяет существование данных для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param spellId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    boolean existsByCharacterIdAndSpellId(UUID characterId, UUID spellId);
}
