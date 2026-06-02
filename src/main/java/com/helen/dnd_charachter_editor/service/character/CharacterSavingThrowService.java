package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSavingThrowRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSavingThrowResponse;

import java.util.List;
import java.util.UUID;

public interface CharacterSavingThrowService {

    List<CharacterSavingThrowResponse> getCharacterSavingThrows(UUID characterId);

    CharacterSavingThrowResponse updateCharacterSavingThrow(
            UUID characterId,
            UUID abilityId,
            SetCharacterSavingThrowRequest request
    );
}
