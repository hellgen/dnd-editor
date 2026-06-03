package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data transfer object for set character race request.
 */
public record SetCharacterRaceRequest(
        @NotNull
        UUID raceId,
        UUID subraceId
) {
}
