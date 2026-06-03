package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data transfer object for set character saving throw request.
 */
public record SetCharacterSavingThrowRequest(
        @NotNull
        @Min(0)
        @Max(1)
        Integer proficiencyLevel
) {
}
