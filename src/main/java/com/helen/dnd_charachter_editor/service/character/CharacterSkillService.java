package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSkillRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSkillResponse;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса `CharacterSkillService`.
 */
public interface CharacterSkillService {

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<CharacterSkillResponse> getCharacterSkills(UUID characterId);

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param skillId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterSkillResponse updateCharacterSkill(
            UUID characterId,
            UUID skillId,
            SetCharacterSkillRequest request
    );
}
