package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;

public class ClassArchetypeMapper {
    public static ClassArchetypeResponse toClassArchetypeResponse(ClassArchetype classArchetype){
        return new ClassArchetypeResponse(
                classArchetype.getId(),
                classArchetype.getCharacterClass().getId(),
                classArchetype.getName(),
                classArchetype.getDescription()
        );
    }
}
