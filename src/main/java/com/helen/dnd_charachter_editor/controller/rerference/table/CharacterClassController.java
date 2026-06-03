package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.CharacterClassResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import com.helen.dnd_charachter_editor.service.reference.table.SpellService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер REST API для обработки запросов `CharacterClassController`.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
public class CharacterClassController {

    private final CharacterClassService characterClassService;
    private final SpellService spellService;

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    @GetMapping
    public List<CharacterClassResponse> getAllClasses() {
        return characterClassService.getAllClasses();
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{classId}")
    public CharacterClassResponse getClassById(
            @PathVariable UUID classId
    ) {
        return characterClassService.getClassResponseById(classId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{classId}/features")
    public List<ClassFeatureResponse> getAllFeatures(
            @PathVariable UUID classId,
            @RequestParam(required = false) Integer level
    ) {
        return characterClassService.getAllFeatures(classId, level);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classFeatureId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{classId}/features/{classFeatureId}")
    public ClassFeatureResponse getClassFeatureById(
            @PathVariable UUID classId,
            @PathVariable UUID classFeatureId
    ) {
        return characterClassService.getClassFeatureById(
                classId,
                classFeatureId
        );
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{classId}/spells")
    public List<SpellResponse> getSpellsByClassId(@PathVariable UUID classId) {
        return spellService.getSpellsByClassId(classId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{classId}/class-archetypes")
    public List<ClassArchetypeResponse> getAllArchetypes(
            @PathVariable UUID classId
    ) {
        return characterClassService.getAllArchetypes(classId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{classId}/class-archetypes/{classArchetypeId}")
    public ClassArchetypeResponse getClassArchetypeById(
            @PathVariable UUID classId,
            @PathVariable UUID classArchetypeId
    ) {
        return characterClassService.getClassArchetypeResponseById(
                classId,
                classArchetypeId
        );
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{classId}/class-archetypes/{classArchetypeId}/features")
    public List<ClassArchetypeFeatureResponse> getAllFeatures(
            @PathVariable UUID classId,
            @PathVariable UUID classArchetypeId,
            @RequestParam(required = false) Integer level
    ) {
        return characterClassService.getAllFeatures(
                classId,
                classArchetypeId,
                level
        );
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param classArchetypeFeatureId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{classId}/class-archetypes/{classArchetypeId}/features/{classArchetypeFeatureId}")
    public ClassArchetypeFeatureResponse getArchetypeFeatureById(
            @PathVariable UUID classId,
            @PathVariable UUID classArchetypeId,
            @PathVariable UUID classArchetypeFeatureId
    ) {
        return characterClassService.getArchetypeFeatureById(
                classId,
                classArchetypeId,
                classArchetypeFeatureId
        );
    }
}
