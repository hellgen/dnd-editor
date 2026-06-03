package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import org.springframework.stereotype.Component;

/**
 * Mapper that converts ability mapper values between layers.
 */
@Component
public class AbilityMapper {

    /**
     * Converts response.
     * @param ability value used by this operation
     * @return result of the operation
     */
    public AbilityResponse toResponse(Ability ability) {
        return new AbilityResponse(
                ability.getId(),
                ability.getCode(),
                ability.getName()
        );
    }
}
