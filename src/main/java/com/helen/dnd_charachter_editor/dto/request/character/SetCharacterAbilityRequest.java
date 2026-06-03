package com.helen.dnd_charachter_editor.dto.request.character;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data transfer object for set character ability request.
 */
public record SetCharacterAbilityRequest(
        @JsonAlias("value")
        @NotNull
        @Min(1)
        @Max(20)
        Integer baseValue
) {
}
