package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;

/**
 * Mapper that converts subrace mapper values between layers.
 */
public class SubraceMapper {
    /**
     * Converts list response.
     * @param subrace value used by this operation
     * @return result of the operation
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
     * Converts description response.
     * @param subrace value used by this operation
     * @return result of the operation
     */
    public static SubraceDescriptionResponse toDescriptionResponse(Subrace subrace) {
        return new SubraceDescriptionResponse(
                subrace.getId(),
                subrace.getName(),
                subrace.getDescription()
        );
    }
}
