package com.helen.dnd_charachter_editor.mapper.referencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.SubraceResponse;
import com.helen.dnd_charachter_editor.entity.referencetable.Subrace;

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