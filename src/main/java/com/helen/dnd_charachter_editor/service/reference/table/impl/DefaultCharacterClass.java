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
 * Default service implementation for default character class operations.
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
     * Returns all classes.
     * @return result of the operation
     */
    @Override
    public List<CharacterClassResponse> getAllClasses() {
        return characterClassRepository.findAll()
                .stream()
                .map(CharacterClassMapper::toCharacterClassResponse)
                .toList();
    }

    /**
     * Returns class response by id.
     * @param classId value used by this operation
     * @return result of the operation
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
     * Returns class by id.
     * @param classId value used by this operation
     * @return result of the operation
     */
    @Override
    public CharacterClass getClassById(UUID classId) {
        return characterClassRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class not found with id: " + classId
                ));
    }

    /**
     * Returns all features.
     * @param classId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
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
     * Returns class feature by id.
     * @param classId value used by this operation
     * @param classFeatureId value used by this operation
     * @return result of the operation
     */
    @Override
    public ClassFeatureResponse getClassFeatureById(
            UUID classId,
            UUID classFeatureId
    ) {
        checkClassExists(classId);

        return classFeatureRepository
                /**
                 * Default service implementation for id operations.
                 */
                .findByIdAndCharacterClassId(classFeatureId, classId)
                /**
                 * Default service implementation for id operations.
                 */
                .map(ClassFeatureMapper::toClassFeatureResponse)
                /**
                 * Default service implementation for id operations.
                 */
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class feature not found with id: "
                                + classFeatureId
                                /**
                                 * Default service implementation for id operations.
                                 */
                                + " for class id: "
                                + classId
                ));
    }

    /**
     * Returns all archetypes.
     * @param classId value used by this operation
     * @return result of the operation
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
     * Returns class archetype response by id.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @return result of the operation
     */
    @Override
    public ClassArchetypeResponse getClassArchetypeResponseById(
            UUID classId,
            UUID classArchetypeId
    ) {
        checkClassExists(classId);

        return classArchetypeRepository
                /**
                 * Default service implementation for id operations.
                 */
                .findByIdAndCharacterClassId(classArchetypeId, classId)
                /**
                 * Default service implementation for id operations.
                 */
                .map(ClassArchetypeMapper::toClassArchetypeResponse)
                /**
                 * Default service implementation for id operations.
                 */
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class archetype not found with id: "
                                + classArchetypeId
                                /**
                                 * Default service implementation for id operations.
                                 */
                                + " for class id: "
                                + classId
                ));
    }

    /**
     * Returns class archetype by id.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @return result of the operation
     */
    @Override
    public ClassArchetype getClassArchetypeById(UUID classId, UUID classArchetypeId) {
        checkClassExists(classId);

        return classArchetypeRepository
                /**
                 * Default service implementation for id operations.
                 */
                .findByIdAndCharacterClassId(classArchetypeId, classId)
                /**
                 * Default service implementation for id operations.
                 */
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class archetype not found with id: "
                                + classArchetypeId
                                /**
                                 * Default service implementation for id operations.
                                 */
                                + " for class id: "
                                + classId
                ));
    }

    /**
     * Returns all features.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
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
     * Returns archetype feature by id.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @param classArchetypeFeatureId value used by this operation
     * @return result of the operation
     */
    @Override
    public ClassArchetypeFeatureResponse getArchetypeFeatureById(
            UUID classId,
            UUID classArchetypeId,
            UUID classArchetypeFeatureId
    ) {
        checkArchetypeBelongsToClass(classId, classArchetypeId);

        return classArchetypeFeatureRepository
                /**
                 * Default service implementation for id operations.
                 */
                .findByIdAndClassArchetypeIdAndClassArchetypeCharacterClassId(
                        classArchetypeFeatureId,
                        classArchetypeId,
                        classId
                )
                /**
                 * Default service implementation for id operations.
                 */
                .map(ClassArchetypeFeatureMapper::toClassArchetypeFeatureResponse)
                /**
                 * Default service implementation for id operations.
                 */
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class archetype feature not found with id: "
                                + classArchetypeFeatureId
                                + " for archetype id: "
                                + classArchetypeId
                                /**
                                 * Default service implementation for id operations.
                                 */
                                + " and class id: "
                                + classId
                ));
    }

    /**
     * Returns available features by level.
     * @param classId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
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
     * Returns available archetype features by level.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
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
     * Executes the check class exists operation.
     * @param classId value used by this operation
     */
    private void checkClassExists(UUID classId) {
        if (!characterClassRepository.existsById(classId)) {
            throw new EntityNotFoundException(
                    "Class not found with id: " + classId
            );
        }
    }

    /**
     * Executes the check archetype belongs to class operation.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
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
     * Executes the check level is valid operation.
     * @param level value used by this operation
     */
    private void checkLevelIsValid(Integer level) {
        if (level == null || level <= 0) {
            throw new IllegalArgumentException(
                    "Level must be greater than 0"
            );
        }
    }

}
