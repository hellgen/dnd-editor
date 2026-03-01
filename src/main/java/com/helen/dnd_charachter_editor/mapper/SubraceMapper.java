package com.helen.dnd_charachter_editor.mapper;

import com.helen.dnd_charachter_editor.dto.response.race_subrace.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.race_subrace.SubraceResponse;
import com.helen.dnd_charachter_editor.entity.Subrace;

public class SubraceMapper {
    public static SubraceResponse toSubraceListResponse(Subrace subrace) {
        return new SubraceResponse(
                subrace.getId(),
                subrace.getName()
        );
    }

    public static SubraceDescriptionResponse toSubraceDescriptionResponse(Subrace subrace) {
        return new SubraceDescriptionResponse(
                subrace.getId(),
                subrace.getName(),
                subrace.getDescription()
        );
    }
}
