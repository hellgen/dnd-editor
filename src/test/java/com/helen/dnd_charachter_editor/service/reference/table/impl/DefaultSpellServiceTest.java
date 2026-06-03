package com.helen.dnd_charachter_editor.service.reference.table.impl;

import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;
import com.helen.dnd_charachter_editor.repository.reference.table.SpellRepository;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Реализация сервиса `DefaultSpellServiceTest`.
 */
class DefaultSpellServiceTest {

    private final SpellRepository spellRepository = mock(SpellRepository.class);
    private final CharacterClassService characterClassService = mock(CharacterClassService.class);
    private final DefaultSpellService service = new DefaultSpellService(spellRepository, characterClassService);

    /**
     * Возвращает данные для запрошенной операции.
     */
    @Test
    void getAllSpellsReturnsSpellResponses() {
        Spell spell = spell(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Волшебная стрела");
        when(spellRepository.findAll()).thenReturn(List.of(spell));

        var response = service.getAllSpells();

        assertEquals(1, response.size());
        assertEquals("Волшебная стрела", response.getFirst().spellName());
    }

    /**
     * Возвращает данные для запрошенной операции.
     */
    @Test
    void getSpellResponseReturnsSpellById() {
        UUID spellId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(spellRepository.findById(spellId)).thenReturn(Optional.of(spell(spellId, "Волшебная стрела")));

        var response = service.getSpellResponse(spellId);

        assertEquals(spellId, response.id());
        assertEquals("Волшебная стрела", response.spellName());
    }

    /**
     * Возвращает данные для запрошенной операции.
     */
    @Test
    void getSpellThrowsWhenSpellDoesNotExist() {
        UUID spellId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(spellRepository.findById(spellId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getSpell(spellId));
    }

    /**
     * Возвращает данные для запрошенной операции.
     */
    @Test
    void getSpellsByClassIdReturnsAvailableClassSpells() {
        UUID classId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        CharacterClass characterClass = characterClass(classId, "Волшебник", true);
        Spell availableSpell = spell(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Волшебная стрела");
        Spell unavailableSpell = spell(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Лечение ран");
        when(characterClassService.getClassById(classId)).thenReturn(characterClass);
        when(spellRepository.findAll()).thenReturn(List.of(availableSpell, unavailableSpell));

        var response = service.getSpellsByClassId(classId);

        assertEquals(1, response.size());
        assertEquals("Волшебная стрела", response.getFirst().spellName());
    }

    /**
     * Проверяет состояние для запрошенной операции.
     */
    @Test
    void isSpellAvailableForClassChecksClassNameAndSpellcasterFlag() {
        CharacterClass wizard = characterClass(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Волшебник", true);
        CharacterClass fighter = characterClass(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Воин", false);
        Spell spell = spell(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Волшебная стрела");

        assertTrue(service.isSpellAvailableForClass(wizard, spell));
        assertFalse(service.isSpellAvailableForClass(fighter, spell));
    }

    /**
     * Выполняет запрошенную операцию.
     * @param spellId параметр, используемый при выполнении операции
     * @param spellName параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Spell spell(UUID spellId, String spellName) {
        Spell spell = new Spell();
        spell.setId(spellId);
        spell.setSpellName(spellName);
        spell.setSpellLevel(1);
        spell.setSpellSchool("Воплощение");
        spell.setCastingTime("1 действие");
        spell.setSpellRange("120 футов");
        spell.setComponents("В, С");
        spell.setDuration("Мгновенная");
        spell.setSpellDescription("Описание");
        return spell;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param classId параметр, используемый при выполнении операции
     * @param className параметр, используемый при выполнении операции
     * @param isSpellcaster параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterClass characterClass(UUID classId, String className, Boolean isSpellcaster) {
        CharacterClass characterClass = new CharacterClass();
        characterClass.setId(classId);
        characterClass.setClassName(className);
        characterClass.setIsSpellcaster(isSpellcaster);
        return characterClass;
    }
}
