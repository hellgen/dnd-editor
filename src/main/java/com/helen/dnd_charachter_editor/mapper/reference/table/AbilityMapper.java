package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import org.springframework.stereotype.Component;

/**
 * Маппер `AbilityMapper` для преобразования данных между слоями приложения.
 */
@Component
public class AbilityMapper {

    /**
     * Преобразует данные для запрошенной операции.
     * @param ability параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public AbilityResponse toResponse(Ability ability) {
        return new AbilityResponse(
                ability.getId(),
                ability.getCode(),
                ability.getName()
        );
    }
}
