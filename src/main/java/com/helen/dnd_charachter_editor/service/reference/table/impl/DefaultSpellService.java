package com.helen.dnd_charachter_editor.service.reference.table.impl;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;
import com.helen.dnd_charachter_editor.mapper.reference.table.SpellMapper;
import com.helen.dnd_charachter_editor.repository.reference.table.SpellRepository;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import com.helen.dnd_charachter_editor.service.reference.table.SpellService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Реализация сервиса `DefaultSpellService`.
 */
@Service
@RequiredArgsConstructor
public class DefaultSpellService implements SpellService {

    private static final Map<String, Set<String>> CLASS_SPELL_NAMES = Map.of(
            "Бард", Set.of(
                    "Малая иллюзия", "Свет", "Громовая волна", "Обнаружение магии", "Невидимость",
                    "Удержание личности", "Большая невидимость", "Удержание чудовища"
            ),
            "Жрец", Set.of(
                    "Свет", "Лечение ран", "Благословение", "Обнаружение магии", "Духовное оружие",
                    "Возрождение", "Изгнание", "Массовое лечение ран"
            ),
            "Друид", Set.of(
                    "Лечение ран", "Обнаружение магии", "Громовая волна", "Паутина", "Изгнание",
                    "Массовое лечение ран"
            ),
            "Паладин", Set.of(
                    "Лечение ран", "Благословение", "Обнаружение магии", "Возрождение"
            ),
            "Следопыт", Set.of(
                    "Лечение ран", "Обнаружение магии", "Паутина"
            ),
            "Чародей", Set.of(
                    "Огненный снаряд", "Луч холода", "Волшебная стрела", "Щит", "Громовая волна",
                    "Туманный шаг", "Невидимость", "Огненный шар", "Контрзаклинание", "Молния",
                    "Ускорение", "Большая невидимость", "Конус холода"
            ),
            "Колдун", Set.of(
                    "Малая иллюзия", "Невидимость", "Удержание личности", "Изгнание", "Удержание чудовища"
            ),
            "Волшебник", Set.of(
                    "Огненный снаряд", "Свет", "Малая иллюзия", "Луч холода", "Волшебная стрела", "Щит",
                    "Обнаружение магии", "Громовая волна", "Туманный шаг", "Невидимость", "Удержание личности",
                    "Паутина", "Огненный шар", "Контрзаклинание", "Молния", "Ускорение", "Изгнание",
                    "Большая невидимость", "Ледяная буря", "Конус холода", "Удержание чудовища"
            )
    );

    private final SpellRepository spellRepository;
    private final CharacterClassService characterClassService;

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    @Override
    public List<SpellResponse> getAllSpells() {
        return spellRepository.findAll().stream()
                .sorted(spellComparator())
                .map(SpellMapper::toSpellResponse)
                .toList();
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param spellId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public SpellResponse getSpellResponse(UUID spellId) {
        return SpellMapper.toSpellResponse(getSpell(spellId));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param spellId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public Spell getSpell(UUID spellId) {
        return spellRepository.findById(spellId)
                .orElseThrow(() -> new EntityNotFoundException("Spell not found with id: " + spellId));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public List<SpellResponse> getSpellsByClassId(UUID classId) {
        CharacterClass characterClass = characterClassService.getClassById(classId);
        return spellRepository.findAll().stream()
                .filter(spell -> isSpellAvailableForClass(characterClass, spell))
                .sorted(spellComparator())
                .map(SpellMapper::toSpellResponse)
                .toList();
    }

    /**
     * Проверяет состояние для запрошенной операции.
     * @param characterClass параметр, используемый при выполнении операции
     * @param spell параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public boolean isSpellAvailableForClass(CharacterClass characterClass, Spell spell) {
        if (characterClass == null || spell == null || !Boolean.TRUE.equals(characterClass.getIsSpellcaster())) {
            return false;
        }
        return CLASS_SPELL_NAMES.getOrDefault(characterClass.getClassName(), Set.of()).contains(spell.getSpellName());
    }

    /**
     * Выполняет запрошенную операцию.
     * @return результат выполнения операции
     */
    private Comparator<Spell> spellComparator() {
        return Comparator.comparing(Spell::getSpellLevel)
                .thenComparing(Spell::getSpellName);
    }
}
