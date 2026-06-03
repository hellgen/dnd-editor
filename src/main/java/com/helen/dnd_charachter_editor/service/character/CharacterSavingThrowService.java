package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSavingThrowRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSavingThrowResponse;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса `CharacterSavingThrowService`.
 */
public interface CharacterSavingThrowService {

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<CharacterSavingThrowResponse> getCharacterSavingThrows(UUID characterId);

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param abilityId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterSavingThrowResponse updateCharacterSavingThrow(
            UUID characterId,
            UUID abilityId,
            SetCharacterSavingThrowRequest request
    );
}
