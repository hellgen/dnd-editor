package com.helen.dnd_charachter_editor.dto.response.character;

import java.util.UUID;

/**
 * Data transfer object for character ability response.
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
