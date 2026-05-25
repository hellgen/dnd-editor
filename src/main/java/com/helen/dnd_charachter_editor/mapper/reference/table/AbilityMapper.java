package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import org.springframework.stereotype.Component;

@Component
public class AbilityMapper {

    public AbilityResponse toResponse(Ability ability) {
        return new AbilityResponse(
                ability.getId(),
                ability.getCode(),
                ability.getName()
        );
    }
}