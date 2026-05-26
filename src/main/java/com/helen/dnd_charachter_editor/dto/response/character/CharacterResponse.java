package com.helen.dnd_charachter_editor.dto.response.character;

import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;

public record CharacterResponse(
        String characterName,
        String raceName,
        String subraceName,
        String className,
        String classArchetypeName,
        int level,
        int maxHealth,
        int currentHealth,
        String appearance,
        int armorClass,
        List<String> inventory,
        Map<String, Integer> wallet,
        List<String> abilityNames,
        List<String> skillNames,
        List<String> spellNames,
        int savingThrowsCount,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
