package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilitiesRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterAbilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller that exposes character ability controller endpoints.
 */
@RestController
@RequestMapping("/characters/{characterId}/abilities")
@RequiredArgsConstructor
public class CharacterAbilityController {

    private final CharacterAbilityService characterAbilityService;

    /**
     * Returns character abilities.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    @GetMapping
    public List<CharacterAbilityResponse> getCharacterAbilities(
            @PathVariable UUID characterId
    ) {
        return characterAbilityService.getCharacterAbilities(characterId);
    }

    /**
     * Sets character ability.
     * @param characterId value used by this operation
     * @param abilityId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
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

    /**
     * Updates character ability.
     * @param characterId value used by this operation
     * @param abilityId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @PutMapping("/{abilityId}")
    public CharacterAbilityResponse updateCharacterAbility(
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

    /**
     * Updates character abilities.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @PutMapping
    public List<CharacterAbilityResponse> updateCharacterAbilities(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterAbilitiesRequest request
    ) {
        return characterAbilityService.setCharacterAbilities(characterId, request);
    }
}
