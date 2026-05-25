package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;

public class SubraceMapper {
    public static SubraceResponse toListResponse(Subrace subrace) {
        return new SubraceResponse(
                subrace.getId(),
                subrace.getName()
        );
    }

    public static SubraceDescriptionResponse toDescriptionResponse(Subrace subrace) {
        return new SubraceDescriptionResponse(
                subrace.getId(),
                subrace.getName(),
                subrace.getDescription()
        );
    }
}