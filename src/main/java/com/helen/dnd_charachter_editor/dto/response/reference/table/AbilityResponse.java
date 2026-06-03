package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Объект передачи данных `AbilityResponse`.
 */
public record AbilityResponse(
        UUID abilityId,
        String code,
        String name
) {
}
