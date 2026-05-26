package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;

import java.util.UUID;

public interface CharacterService {
    CharacterResponse createCharacter(CreateCharacterRequest createCharacterRequest);

    CharacterResponse updateCharacter(UUID characterId, CreateCharacterRequest createCharacterRequest);
}
