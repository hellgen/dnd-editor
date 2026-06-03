package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.CharacterClassResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса `CharacterClassService`.
 */
public interface CharacterClassService {
    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    List<CharacterClassResponse> getAllClasses();

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterClassResponse getClassResponseById(UUID classId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterClass getClassById(UUID classId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<ClassFeatureResponse> getAllFeatures(UUID classId, Integer level);

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classFeatureId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    ClassFeatureResponse getClassFeatureById(
            UUID classId,
            UUID classFeatureId
    );

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<ClassArchetypeResponse> getAllArchetypes(UUID classId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    ClassArchetypeResponse getClassArchetypeResponseById(
            UUID classId,
            UUID classArchetypeId
    );

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    ClassArchetype getClassArchetypeById(
            UUID classId,
            UUID classArchetypeId
    );

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<ClassArchetypeFeatureResponse> getAllFeatures(
            UUID classId,
            UUID classArchetypeId,
            Integer level
    );

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param classArchetypeFeatureId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    ClassArchetypeFeatureResponse getArchetypeFeatureById(
            UUID classId,
            UUID classArchetypeId,
            UUID classArchetypeFeatureId
    );

}
