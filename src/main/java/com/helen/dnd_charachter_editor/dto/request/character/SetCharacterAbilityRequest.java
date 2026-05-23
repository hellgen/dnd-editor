package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SetCharacterAbilityRequest(
        @NotNull
        @Min(1)
        @Max(20)
        Integer value
) {
}