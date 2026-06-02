package com.helen.dnd_charachter_editor.dto.request.character;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SetCharacterRaceRequest(
        @NotNull
        UUID raceId,
        UUID subraceId
) {
}
