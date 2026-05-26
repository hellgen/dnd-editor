package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.service.character.DndRulesService;
import org.springframework.stereotype.Service;

@Service
public class DefaultDndRulesService implements DndRulesService {

    @Override
    public int calculateAbilityModifier(int finalValue) {
        return Math.floorDiv(finalValue - 10, 2);
    }

    @Override
    public int calculateProficiencyBonus(int level) {
        return 2 + (level - 1) / 4;
    }
}
