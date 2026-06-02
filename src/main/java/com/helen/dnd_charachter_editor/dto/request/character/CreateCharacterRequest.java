package com.helen.dnd_charachter_editor.dto.request.character;

import java.util.List;
import java.util.UUID;

public record CreateCharacterRequest(
        String characterName,
        UUID raceId,
        UUID subraceId,
        UUID classID,
        UUID classArchetypeId,
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

        List<UUID> abilities,
        List<UUID> skills,
        List<UUID> spells,
        int savingThrowsCount
) {
}
