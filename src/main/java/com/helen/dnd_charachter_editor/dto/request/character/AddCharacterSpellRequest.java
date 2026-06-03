package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data transfer object for add character spell request.
 */
public record AddCharacterSpellRequest(
        @NotNull
        UUID spellId
) {
}
