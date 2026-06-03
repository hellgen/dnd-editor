package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Объект передачи данных `SetCharacterAbilitiesRequest`.
 */
public record SetCharacterAbilitiesRequest(
        @NotEmpty
        List<@Valid SetCharacterAbilityValueRequest> abilities
) {
}
