package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterRaceRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;

import java.util.UUID;

public interface CharacterService {
    CharacterResponse createCharacter(CreateCharacterRequest createCharacterRequest);

    CharacterResponse getCharacter(UUID characterId);

    CharacterResponse updateCharacter(UUID characterId, CreateCharacterRequest createCharacterRequest);

    CharacterResponse updateCharacterLevel(UUID characterId, Integer level);

    CharacterResponse updateCharacterHealth(UUID characterId, Integer maxHealth, Integer currentHealth);

    CharacterResponse applyCharacterRace(UUID characterId, SetCharacterRaceRequest request);

    CharacterResponse updateCharacterRace(UUID characterId, SetCharacterRaceRequest request);

    CharacterResponse applyCharacterClass(UUID characterId, SetCharacterClassRequest request);

    CharacterResponse updateCharacterClass(UUID characterId, SetCharacterClassRequest request);

    void deleteCharacter(UUID characterId);
}
