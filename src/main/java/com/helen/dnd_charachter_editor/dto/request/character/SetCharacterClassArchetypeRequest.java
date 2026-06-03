package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Объект передачи данных `SetCharacterClassArchetypeRequest`.
 */
public record SetCharacterClassArchetypeRequest(
        @NotNull
        UUID classArchetypeId
) {
}
