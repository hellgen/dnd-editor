package com.helen.dnd_charachter_editor.mapper.referencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.entity.referencetable.ClassArchetype;

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
