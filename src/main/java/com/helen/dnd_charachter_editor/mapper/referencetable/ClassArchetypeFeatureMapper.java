package com.helen.dnd_charachter_editor.mapper.referencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.entity.referencetable.ClassArchetypeFeature;

public class ClassArchetypeFeatureMapper {
    public static ClassArchetypeFeatureResponse toClassArchetypeFeatureResponse(ClassArchetypeFeature classArchetypeFeature) {
        return new ClassArchetypeFeatureResponse(
                classArchetypeFeature.getId(),
                classArchetypeFeature.getClassArchetype().getId(),
                classArchetypeFeature.getFeatureName(),
                classArchetypeFeature.getFeatureDescription(),
                classArchetypeFeature.getLevelRequired()
        );
    }
}
