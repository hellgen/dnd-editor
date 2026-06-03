package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Объект передачи данных `SetCharacterSavingThrowRequest`.
 */
public record SetCharacterSavingThrowRequest(
        @NotNull
        @Min(0)
        @Max(1)
        Integer proficiencyLevel
) {
}
