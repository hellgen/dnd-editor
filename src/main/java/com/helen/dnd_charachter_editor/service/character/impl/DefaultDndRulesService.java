package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.service.character.DndRulesService;
import org.springframework.stereotype.Service;

/**
 * Default service implementation for default dnd rules service operations.
 */
@Service
public class DefaultDndRulesService implements DndRulesService {

    /**
     * Calculates ability modifier.
     * @param finalValue value used by this operation
     * @return result of the operation
     */
    @Override
    public int calculateAbilityModifier(int finalValue) {
        return Math.floorDiv(finalValue - 10, 2);
    }

    /**
     * Calculates proficiency bonus.
     * @param level value used by this operation
     * @return result of the operation
     */
    @Override
    public int calculateProficiencyBonus(int level) {
        return 2 + (level - 1) / 4;
    }
}
