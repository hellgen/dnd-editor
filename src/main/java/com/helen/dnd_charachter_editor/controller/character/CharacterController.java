package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    private CharacterResponse createCharacter(CreateCharacterRequest createCharacterRequest) {
        return characterService.createCharacter(createCharacterRequest);
    }
}
