package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;

/**
 * Маппер `SubraceMapper` для преобразования данных между слоями приложения.
 */
public class SubraceMapper {
    /**
     * Преобразует данные для запрошенной операции.
     * @param subrace параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static SubraceResponse toListResponse(Subrace subrace) {
        return new SubraceResponse(
                subrace.getId(),
                subrace.getRace().getId(),
                subrace.getRace().getName(),
                subrace.getName(),
                subrace.getDescription()
        );
    }

    /**
     * Преобразует данные для запрошенной операции.
     * @param subrace параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static SubraceDescriptionResponse toDescriptionResponse(Subrace subrace) {
        return new SubraceDescriptionResponse(
                subrace.getId(),
                subrace.getName(),
                subrace.getDescription()
        );
    }
}
