package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Объект передачи данных `ClassArchetypeFeatureResponse`.
 */
public record ClassArchetypeFeatureResponse(
        UUID id,
        UUID classArchetypeId,
        String featureName,
        String featureDescription,
        Integer levelRequired
) {
}
