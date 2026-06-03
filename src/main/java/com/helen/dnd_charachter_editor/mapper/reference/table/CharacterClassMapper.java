package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.CharacterClassResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;

/**
 * Маппер `CharacterClassMapper` для преобразования данных между слоями приложения.
 */
public class CharacterClassMapper {
    /**
     * Преобразует данные для запрошенной операции.
     * @param characterClass параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static CharacterClassResponse toCharacterClassResponse(CharacterClass characterClass) {
        return new CharacterClassResponse(
                characterClass.getId(),
                characterClass.getClassName(),
                characterClass.getClassDescription(),
                characterClass.getIsSpellcaster(),
                characterClass.getSpellcastingStartLevel()
        );
    }
}
