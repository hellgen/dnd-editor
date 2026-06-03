package com.helen.dnd_charachter_editor.dto.response.character;

import java.util.UUID;

/**
 * Объект передачи данных `CharacterSavingThrowResponse`.
 */
public record CharacterSavingThrowResponse(
        UUID id,
        UUID characterId,
        UUID abilityId,
        String abilityCode,
        String abilityName,
        Integer abilityModifier,
        Integer proficiencyLevel,
        Integer proficiencyBonus,
        Integer totalModifier
) {
}
