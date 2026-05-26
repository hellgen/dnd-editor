package com.helen.dnd_charachter_editor.service.character;

public interface DndRulesService {

    int calculateAbilityModifier(int finalValue);

    int calculateProficiencyBonus(int level);
}
