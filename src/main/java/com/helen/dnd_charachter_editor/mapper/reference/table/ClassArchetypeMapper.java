package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;

/**
 * Маппер `ClassArchetypeMapper` для преобразования данных между слоями приложения.
 */
public class ClassArchetypeMapper {
    /**
     * Преобразует данные для запрошенной операции.
     * @param classArchetype параметр, используемый при выполнении операции
     * @return результат выполнения операции
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
