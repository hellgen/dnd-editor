package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;

public interface CharacterService {
    CharacterResponse createCharacter(CreateCharacterRequest createCharacterRequest);
}
