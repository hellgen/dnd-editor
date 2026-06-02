package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.SubraceFeature;

public class SubraceFeatureMapper {

    public static SubraceFeatureResponse toResponse(SubraceFeature subraceFeature) {
        return new SubraceFeatureResponse(
                subraceFeature.getId(),
                subraceFeature.getSubrace().getRace().getId(),
                subraceFeature.getSubrace().getRace().getName(),
                subraceFeature.getSubrace().getId(),
                subraceFeature.getSubrace().getName(),
                subraceFeature.getFeatureName(),
                subraceFeature.getFeatureDescription()
        );
    }
}
