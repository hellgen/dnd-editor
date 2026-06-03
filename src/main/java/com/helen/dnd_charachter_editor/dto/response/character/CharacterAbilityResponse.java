package com.helen.dnd_charachter_editor.dto.response.character;

import java.util.UUID;

/**
 * Объект передачи данных `CharacterAbilityResponse`.
 */
public record CharacterAbilityResponse(
        UUID id,
        UUID characterId,
        UUID abilityId,
        String abilityCode,
        String abilityName,
        Integer baseValue,
        Integer raceBonus,
        Integer subraceBonus,
        Integer totalValue,
        Integer modifier
) {
}
