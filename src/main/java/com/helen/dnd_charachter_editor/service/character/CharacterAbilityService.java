package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilitiesRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса `CharacterAbilityService`.
 */
public interface CharacterAbilityService {

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<CharacterAbilityResponse> getCharacterAbilities(UUID characterId);

    /**
     * Устанавливает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param abilityId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterAbilityResponse setCharacterAbility(
            UUID characterId,
            UUID abilityId,
            SetCharacterAbilityRequest request
    );

    /**
     * Устанавливает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<CharacterAbilityResponse> setCharacterAbilities(
            UUID characterId,
            SetCharacterAbilitiesRequest request
    );
}
