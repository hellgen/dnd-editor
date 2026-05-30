package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterService;
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

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteCharacter(@RequestParam UUID characterId) {
        characterService.deleteCharacter(characterId);
    }
}
