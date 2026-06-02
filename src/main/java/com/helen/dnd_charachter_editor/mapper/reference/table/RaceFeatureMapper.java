package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceFeature;

public class RaceFeatureMapper {

    public static RaceFeatureResponse toResponse(RaceFeature raceFeature) {
        return new RaceFeatureResponse(
                raceFeature.getId(),
                raceFeature.getRace().getId(),
                raceFeature.getRace().getName(),
                raceFeature.getFeatureName(),
                raceFeature.getFeatureDescription()
        );
    }
}
