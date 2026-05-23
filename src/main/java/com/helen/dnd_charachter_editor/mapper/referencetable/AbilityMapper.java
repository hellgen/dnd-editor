package com.helen.dnd_charachter_editor.mapper.referencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.AbilityResponse;
import com.helen.dnd_charachter_editor.entity.referencetable.Ability;
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