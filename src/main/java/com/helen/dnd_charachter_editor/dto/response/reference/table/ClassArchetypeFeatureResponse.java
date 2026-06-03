package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Data transfer object for class archetype feature response.
 */
public record ClassArchetypeFeatureResponse(
        UUID id,
        UUID classArchetypeId,
        String featureName,
        String featureDescription,
        Integer levelRequired
) {
}
