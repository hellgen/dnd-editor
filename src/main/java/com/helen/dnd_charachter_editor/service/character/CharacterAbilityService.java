package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilitiesRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service contract for character ability service operations.
 */
public interface CharacterAbilityService {

    /**
     * Returns character abilities.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    List<CharacterAbilityResponse> getCharacterAbilities(UUID characterId);

    /**
     * Sets character ability.
     * @param characterId value used by this operation
     * @param abilityId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    CharacterAbilityResponse setCharacterAbility(
            UUID characterId,
            UUID abilityId,
            SetCharacterAbilityRequest request
    );

    /**
     * Sets character abilities.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    List<CharacterAbilityResponse> setCharacterAbilities(
            UUID characterId,
            SetCharacterAbilitiesRequest request
    );
}
