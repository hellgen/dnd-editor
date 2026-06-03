package com.helen.dnd_charachter_editor.dto.request.character;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Объект передачи данных `SetCharacterAbilityValueRequest`.
 */
public record SetCharacterAbilityValueRequest(
        @NotNull
        UUID abilityId,
        @JsonAlias("value")
        @NotNull
        @Min(1)
        @Max(20)
        Integer baseValue
) {
}
