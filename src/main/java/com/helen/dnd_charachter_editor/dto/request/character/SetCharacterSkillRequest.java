package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Объект передачи данных `SetCharacterSkillRequest`.
 */
public record SetCharacterSkillRequest(
        @NotNull
        @Min(0)
        @Max(2)
        Integer proficiencyLevel
) {
}
