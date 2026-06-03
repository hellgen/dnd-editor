package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassArchetypeRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterRaceRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    private CharacterResponse createCharacter(@RequestBody CreateCharacterRequest createCharacterRequest) {
        return characterService.createCharacter(createCharacterRequest);
    }

    @GetMapping("/{characterId}")
    private CharacterResponse getCharacter(@PathVariable UUID characterId) {
        return characterService.getCharacter(characterId);
    }

    @PutMapping("/{characterId}")
    private CharacterResponse updateCharacter(
            @PathVariable UUID characterId,
            @RequestBody CreateCharacterRequest createCharacterRequest
    ) {
        return characterService.updateCharacter(characterId, createCharacterRequest);
    }

    @PutMapping("/{characterId}/level")
    private CharacterResponse updateCharacterLevel(
            @PathVariable UUID characterId,
            @RequestParam Integer level
    ) {
        return characterService.updateCharacterLevel(characterId, level);
    }

    @PutMapping("/{characterId}/health")
    private CharacterResponse updateCharacterHealth(
            @PathVariable UUID characterId,
            @RequestParam Integer maxHealth,
            @RequestParam Integer currentHealth
    ) {
        return characterService.updateCharacterHealth(characterId, maxHealth, currentHealth);
    }

    @PostMapping("/{characterId}/class")
    public CharacterResponse applyCharacterClass(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassRequest request
    ) {
        return characterService.applyCharacterClass(characterId, request);
    }

    @PutMapping("/{characterId}/class")
    public CharacterResponse updateCharacterClass(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassRequest request
    ) {
        return characterService.updateCharacterClass(characterId, request);
    }

    @PostMapping("/{characterId}/class-archetype")
    public CharacterResponse applyCharacterClassArchetype(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassArchetypeRequest request
    ) {
        return characterService.applyCharacterClassArchetype(characterId, request);
    }

    @PutMapping("/{characterId}/class-archetype")
    public CharacterResponse updateCharacterClassArchetype(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassArchetypeRequest request
    ) {
        return characterService.updateCharacterClassArchetype(characterId, request);
    }

    @PostMapping("/{characterId}/race")
    public CharacterResponse applyCharacterRace(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterRaceRequest request
    ) {
        return characterService.applyCharacterRace(characterId, request);
    }

    @PutMapping("/{characterId}/race")
    public CharacterResponse updateCharacterRace(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterRaceRequest request
    ) {
        return characterService.updateCharacterRace(characterId, request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteCharacter(@RequestParam UUID characterId) {
        characterService.deleteCharacter(characterId);
    }
}
