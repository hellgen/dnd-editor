package com.helen.dnd_charachter_editor.dto.response.race_subrace;

import java.util.List;
import java.util.UUID;

public record RaceDescriptionResponse(
        UUID id,
        int age,
        int height,
        int speed,
        String languages,
        String description
) {
}
