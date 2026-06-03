package com.helen.dnd_charachter_editor.service.reference.table.impl;

import com.helen.dnd_charachter_editor.dto.response.reference.table.CharacterClassResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetypeFeature;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassFeature;
import com.helen.dnd_charachter_editor.mapper.reference.table.CharacterClassMapper;
import com.helen.dnd_charachter_editor.mapper.reference.table.ClassArchetypeFeatureMapper;
import com.helen.dnd_charachter_editor.mapper.reference.table.ClassArchetypeMapper;
import com.helen.dnd_charachter_editor.mapper.reference.table.ClassFeatureMapper;
import com.helen.dnd_charachter_editor.repository.reference.table.CharacterClassRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.ClassArchetypeFeatureRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.ClassArchetypeRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.ClassFeatureRepository;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Реализация сервиса `DefaultCharacterClass`.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultCharacterClass implements CharacterClassService {

    private final CharacterClassRepository characterClassRepository;
    private final ClassFeatureRepository classFeatureRepository;
    private final ClassArchetypeRepository classArchetypeRepository;
    private final ClassArchetypeFeatureRepository classArchetypeFeatureRepository;

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    @Override
    public List<CharacterClassResponse> getAllClasses() {
        return characterClassRepository.findAll()
                .stream()
                .map(CharacterClassMapper::toCharacterClassResponse)
                .toList();
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public CharacterClassResponse getClassResponseById(UUID classId) {
        return characterClassRepository.findById(classId)
                .map(CharacterClassMapper::toCharacterClassResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class not found with id: " + classId
                ));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public CharacterClass getClassById(UUID classId) {
        return characterClassRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class not found with id: " + classId
                ));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public List<ClassFeatureResponse> getAllFeatures(UUID classId, Integer level) {
        checkClassExists(classId);

        List<ClassFeature> classFeatures = level == null
                ? classFeatureRepository.findAllByCharacterClassIdOrderByLevelRequiredAsc(classId)
                : getAvailableFeaturesByLevel(classId, level);

        return classFeatures.stream()
                .map(ClassFeatureMapper::toClassFeatureResponse)
                .toList();
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classFeatureId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public ClassFeatureResponse getClassFeatureById(
            UUID classId,
            UUID classFeatureId
    ) {
        checkClassExists(classId);

        return classFeatureRepository
                .findByIdAndCharacterClassId(classFeatureId, classId)
                .map(ClassFeatureMapper::toClassFeatureResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class feature not found with id: "
                                + classFeatureId
                                + " for class id: "
                                + classId
                ));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public List<ClassArchetypeResponse> getAllArchetypes(UUID classId) {
        checkClassExists(classId);

        return classArchetypeRepository
                .findAllByCharacterClassId(classId)
                .stream()
                .map(ClassArchetypeMapper::toClassArchetypeResponse)
                .toList();
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public ClassArchetypeResponse getClassArchetypeResponseById(
            UUID classId,
            UUID classArchetypeId
    ) {
        checkClassExists(classId);

        return classArchetypeRepository
                .findByIdAndCharacterClassId(classArchetypeId, classId)
                .map(ClassArchetypeMapper::toClassArchetypeResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class archetype not found with id: "
                                + classArchetypeId
                                + " for class id: "
                                + classId
                ));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public ClassArchetype getClassArchetypeById(UUID classId, UUID classArchetypeId) {
        checkClassExists(classId);

        return classArchetypeRepository
                .findByIdAndCharacterClassId(classArchetypeId, classId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class archetype not found with id: "
                                + classArchetypeId
                                + " for class id: "
                                + classId
                ));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public List<ClassArchetypeFeatureResponse> getAllFeatures(
            UUID classId,
            UUID classArchetypeId,
            Integer level
    ) {
        checkArchetypeBelongsToClass(classId, classArchetypeId);

        List<ClassArchetypeFeature> features = level == null
                ? classArchetypeFeatureRepository
                .findAllByClassArchetypeIdAndClassArchetypeCharacterClassId(
                        classArchetypeId,
                        classId
                )
                : getAvailableArchetypeFeaturesByLevel(classId, classArchetypeId, level);

        return features.stream()
                .map(ClassArchetypeFeatureMapper::toClassArchetypeFeatureResponse)
                .toList();
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param classArchetypeFeatureId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    public ClassArchetypeFeatureResponse getArchetypeFeatureById(
            UUID classId,
            UUID classArchetypeId,
            UUID classArchetypeFeatureId
    ) {
        checkArchetypeBelongsToClass(classId, classArchetypeId);

        return classArchetypeFeatureRepository
                .findByIdAndClassArchetypeIdAndClassArchetypeCharacterClassId(
                        classArchetypeFeatureId,
                        classArchetypeId,
                        classId
                )
                .map(ClassArchetypeFeatureMapper::toClassArchetypeFeatureResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class archetype feature not found with id: "
                                + classArchetypeFeatureId
                                + " for archetype id: "
                                + classArchetypeId
                                + " and class id: "
                                + classId
                ));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private List<ClassFeature> getAvailableFeaturesByLevel(UUID classId, Integer level) {
        checkLevelIsValid(level);

        return classFeatureRepository
                .findAllByCharacterClassIdAndLevelRequiredLessThanEqualOrderByLevelRequiredAsc(
                        classId,
                        level
                );
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private List<ClassArchetypeFeature> getAvailableArchetypeFeaturesByLevel(
            UUID classId,
            UUID classArchetypeId,
            Integer level
    ) {
        checkLevelIsValid(level);

        return classArchetypeFeatureRepository
                .findAllByClassArchetypeIdAndClassArchetypeCharacterClassIdAndLevelRequiredLessThanEqualOrderByLevelRequiredAsc(
                        classArchetypeId,
                        classId,
                        level
                );
    }

    /**
     * Проверяет условие для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     */
    private void checkClassExists(UUID classId) {
        if (!characterClassRepository.existsById(classId)) {
            throw new EntityNotFoundException(
                    "Class not found with id: " + classId
            );
        }
    }

    /**
     * Проверяет условие для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     */
    private void checkArchetypeBelongsToClass(
            UUID classId,
            UUID classArchetypeId
    ) {
        checkClassExists(classId);

        boolean exists = classArchetypeRepository
                .findByIdAndCharacterClassId(classArchetypeId, classId)
                .isPresent();

        if (!exists) {
            throw new EntityNotFoundException(
                    "Class archetype not found with id: "
                            + classArchetypeId
                            + " for class id: "
                            + classId
            );
        }
    }


    /**
     * Проверяет условие для запрошенной операции.
     * @param level параметр, используемый при выполнении операции
     */
    private void checkLevelIsValid(Integer level) {
        if (level == null || level <= 0) {
            throw new IllegalArgumentException(
                    "Level must be greater than 0"
            );
        }
    }

}
