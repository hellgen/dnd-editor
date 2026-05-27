package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassFeature;

public class ClassFeatureMapper{
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
