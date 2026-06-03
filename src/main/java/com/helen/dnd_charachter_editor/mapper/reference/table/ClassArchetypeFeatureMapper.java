package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetypeFeature;

/**
 * Mapper that converts class archetype feature mapper values between layers.
 */
public class ClassArchetypeFeatureMapper {
    /**
     * Converts class archetype feature response.
     * @param classArchetypeFeature value used by this operation
     * @return result of the operation
     */
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
