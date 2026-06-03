package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса `SpellService`.
 */
public interface SpellService {

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    List<SpellResponse> getAllSpells();

    /**
     * Возвращает данные для запрошенной операции.
     * @param spellId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    SpellResponse getSpellResponse(UUID spellId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param spellId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Spell getSpell(UUID spellId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<SpellResponse> getSpellsByClassId(UUID classId);

    /**
     * Проверяет состояние для запрошенной операции.
     * @param characterClass параметр, используемый при выполнении операции
     * @param spell параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    boolean isSpellAvailableForClass(CharacterClass characterClass, Spell spell);
}
