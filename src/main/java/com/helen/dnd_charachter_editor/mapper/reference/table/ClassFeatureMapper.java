package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassFeature;

/**
 * Mapper that converts class feature mapper values between layers.
 */
public class ClassFeatureMapper{
    /**
     * Converts class feature response.
     * @param classFeature value used by this operation
     * @return result of the operation
     */
    public static ClassFeatureResponse toClassFeatureResponse(ClassFeature classFeature){
        return new ClassFeatureResponse(
                classFeature.getId(),
                classFeature.getCharacterClass().getId(),
                classFeature.getFeatureName(),
                classFeature.getFeatureDescription(),
                classFeature.getLevelRequired()
        );
    }
}
