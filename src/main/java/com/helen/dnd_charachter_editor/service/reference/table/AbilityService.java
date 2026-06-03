package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса `AbilityService`.
 */
public interface AbilityService {

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    List<AbilityResponse> getAllAbilities();

    /**
     * Возвращает данные для запрошенной операции.
     * @param abilityId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    AbilityResponse getAbility(UUID abilityId);
}
