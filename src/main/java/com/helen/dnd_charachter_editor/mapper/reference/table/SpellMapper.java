package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;

/**
 * Маппер `SpellMapper` для преобразования данных между слоями приложения.
 */
public class SpellMapper {

    /**
     * Преобразует данные для запрошенной операции.
     * @param spell параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static SpellResponse toSpellResponse(Spell spell) {
        return new SpellResponse(
                spell.getId(),
                spell.getSpellName(),
                spell.getSpellLevel(),
                spell.getSpellSchool(),
                spell.getCastingTime(),
                spell.getSpellRange(),
                spell.getComponents(),
                spell.getDuration(),
                spell.getSpellDescription()
        );
    }
}
