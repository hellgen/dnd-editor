package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSavingThrowRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSavingThrowResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service contract for character saving throw service operations.
 */
public interface CharacterSavingThrowService {

    /**
     * Returns character saving throws.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    List<CharacterSavingThrowResponse> getCharacterSavingThrows(UUID characterId);

    /**
     * Updates character saving throw.
     * @param characterId value used by this operation
     * @param abilityId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    CharacterSavingThrowResponse updateCharacterSavingThrow(
            UUID characterId,
            UUID abilityId,
            SetCharacterSavingThrowRequest request
    );
}
