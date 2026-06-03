package com.helen.dnd_charachter_editor.service.character;

/**
 * Контракт сервиса `DndRulesService`.
 */
public interface DndRulesService {

    /**
     * Вычисляет значение для запрошенной операции.
     * @param finalValue параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    int calculateAbilityModifier(int finalValue);

    /**
     * Вычисляет значение для запрошенной операции.
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    int calculateProficiencyBonus(int level);
}
