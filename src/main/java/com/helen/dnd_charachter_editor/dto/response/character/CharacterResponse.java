package com.helen.dnd_charachter_editor.dto.response.character;

import java.util.List;
import java.time.OffsetDateTime;

public record CharacterResponse(
        String characterName,
        String race,
        String subrace,
        String characterClass,
        String classArchetype,
        int level,
        int maxHealth,
        int currentHealth,
        String appearance,
        int armorClass,
        List<String> inventory,

        int platinum,
        int gold,
        int electrum,
        int silver,
        int copper,

        List<String> abilities,
        List<String> skills,
        List<String> spells,
        int savingThrowsCount,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
