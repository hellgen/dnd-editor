package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSavingThrowRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSavingThrowResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterSavingThrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller that exposes character saving throw controller endpoints.
 */
@RestController
@RequestMapping("/characters/{characterId}/saving-throws")
@RequiredArgsConstructor
public class CharacterSavingThrowController {

    private final CharacterSavingThrowService characterSavingThrowService;

    /**
     * Returns character saving throws.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    @GetMapping
    public List<CharacterSavingThrowResponse> getCharacterSavingThrows(
            @PathVariable UUID characterId
    ) {
        return characterSavingThrowService.getCharacterSavingThrows(characterId);
    }

    /**
     * Updates character saving throw.
     * @param characterId value used by this operation
     * @param abilityId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @PatchMapping("/{abilityId}")
    public CharacterSavingThrowResponse updateCharacterSavingThrow(
            @PathVariable UUID characterId,
            @PathVariable UUID abilityId,
            @Valid @RequestBody SetCharacterSavingThrowRequest request
    ) {
        return characterSavingThrowService.updateCharacterSavingThrow(characterId, abilityId, request);
    }
}
