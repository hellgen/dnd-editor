package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceFeature;

/**
 * Mapper that converts race feature mapper values between layers.
 */
public class RaceFeatureMapper {

    /**
     * Converts response.
     * @param raceFeature value used by this operation
     * @return result of the operation
     */
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
