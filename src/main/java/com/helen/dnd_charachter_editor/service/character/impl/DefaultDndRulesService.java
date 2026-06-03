package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.service.character.DndRulesService;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса `DefaultDndRulesService`.
 */
@Service
public class DefaultDndRulesService implements DndRulesService {

    /**
     * Вычисляет значение для запрошенной операции.
     * @param finalValue параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public int calculateAbilityModifier(int finalValue) {
        return Math.floorDiv(finalValue - 10, 2);
    }

    /**
     * Вычисляет значение для запрошенной операции.
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public int calculateProficiencyBonus(int level) {
        return 2 + (level - 1) / 4;
    }
}
