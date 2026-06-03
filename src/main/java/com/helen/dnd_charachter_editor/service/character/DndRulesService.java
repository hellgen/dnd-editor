package com.helen.dnd_charachter_editor.service.character;

/**
 * Service contract for dnd rules service operations.
 */
public interface DndRulesService {

    /**
     * Calculates ability modifier.
     * @param finalValue value used by this operation
     * @return result of the operation
     */
    int calculateAbilityModifier(int finalValue);

    /**
     * Calculates proficiency bonus.
     * @param level value used by this operation
     * @return result of the operation
     */
    int calculateProficiencyBonus(int level);
}
