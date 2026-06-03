package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

/**
 * Data transfer object for class feature response.
 */
public record ClassFeatureResponse(
        UUID id,
        UUID classId,
        String featureName,
        String featureDescription,
        Integer levelRequired
) {
}
