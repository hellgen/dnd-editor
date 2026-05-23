package com.helen.dnd_charachter_editor.dto.response.referencetable;

import java.util.UUID;

public record ClassFeatureResponse(
        UUID id,
        UUID classId,
        String featureName,
        String featureDescription,
        Integer levelRequired
) {
}
