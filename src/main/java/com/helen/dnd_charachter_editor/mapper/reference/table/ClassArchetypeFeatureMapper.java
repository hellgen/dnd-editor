package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetypeFeature;

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
