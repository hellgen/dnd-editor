package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterAbilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/characters/{characterId}/abilities")
@RequiredArgsConstructor
public class CharacterAbilityController {

    private final CharacterAbilityService characterAbilityService;

    @PostMapping("/{abilityId}")
    public CharacterAbilityResponse setCharacterAbility(
            @PathVariable UUID characterId,
            @PathVariable UUID abilityId,
            @Valid @RequestBody SetCharacterAbilityRequest request
    ) {
        return characterAbilityService.setCharacterAbility(
                characterId,
                abilityId,
                request
        );
    }
}