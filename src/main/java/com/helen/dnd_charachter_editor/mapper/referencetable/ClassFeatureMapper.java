package com.helen.dnd_charachter_editor.mapper.referencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.entity.referencetable.ClassFeature;

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
