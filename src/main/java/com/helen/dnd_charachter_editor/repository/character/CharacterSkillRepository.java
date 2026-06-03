package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `CharacterSkillRepository` для доступа к данным.
 */
public interface CharacterSkillRepository extends JpaRepository<CharacterSkill, UUID> {
    /**
     * Находит данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<CharacterSkill> findAllByCharacterId(UUID characterId);

    /**
     * Находит данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param skillId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<CharacterSkill> findByCharacterIdAndSkillId(
            UUID characterId,
            UUID skillId
    );
}
