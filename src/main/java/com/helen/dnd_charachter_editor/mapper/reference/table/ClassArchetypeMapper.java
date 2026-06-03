package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;

/**
 * Mapper that converts class archetype mapper values between layers.
 */
public class ClassArchetypeMapper {
    /**
     * Converts class archetype response.
     * @param classArchetype value used by this operation
     * @return result of the operation
     */
    public static ClassArchetypeResponse toClassArchetypeResponse(ClassArchetype classArchetype){
        return new ClassArchetypeResponse(
                classArchetype.getId(),
                classArchetype.getCharacterClass().getId(),
                classArchetype.getName(),
                classArchetype.getDescription()
        );
    }
}
