package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data transfer object for set character class request.
 */
public record SetCharacterClassRequest(
        @NotNull
        UUID classId,
        UUID classArchetypeId
) {
}
