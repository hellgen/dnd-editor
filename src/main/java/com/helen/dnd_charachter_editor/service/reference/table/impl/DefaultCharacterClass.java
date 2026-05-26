package com.helen.dnd_charachter_editor.service.reference.table.impl;

import com.helen.dnd_charachter_editor.dto.response.reference.table.CharacterClassResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import com.helen.dnd_charachter_editor.mapper.reference.table.CharacterClassMapper;
import com.helen.dnd_charachter_editor.mapper.reference.table.ClassArchetypeFeatureMapper;
import com.helen.dnd_charachter_editor.mapper.reference.table.ClassArchetypeMapper;
import com.helen.dnd_charachter_editor.mapper.reference.table.ClassFeatureMapper;
import com.helen.dnd_charachter_editor.repository.referencetable.CharacterClassRepository;
import com.helen.dnd_charachter_editor.repository.referencetable.ClassArchetypeFeatureRepository;
import com.helen.dnd_charachter_editor.repository.referencetable.ClassArchetypeRepository;
import com.helen.dnd_charachter_editor.repository.referencetable.ClassFeatureRepository;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultCharacterClass implements CharacterClassService {

    private final CharacterClassRepository characterClassRepository;
    private final ClassFeatureRepository classFeatureRepository;
    private final ClassArchetypeRepository classArchetypeRepository;
    private final ClassArchetypeFeatureRepository classArchetypeFeatureRepository;

    @Override
    public List<CharacterClassResponse> getAllClasses() {
        return characterClassRepository.findAll()
                .stream()
                .map(CharacterClassMapper::toCharacterClassResponse)
                .toList();
    }

    @Override
    public CharacterClassResponse getClassResponseById(UUID classId) {
        return characterClassRepository.findById(classId)
                .map(CharacterClassMapper::toCharacterClassResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class not found with id: " + classId
                ));
    }

    @Override
    public CharacterClass getClassById(UUID classId) {
        return characterClassRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class not found with id: " + classId
                ));
    }

    @Override
    public List<ClassFeatureResponse> getAllFeaturesByLevel(
            UUID classId,
            Integer level
    ) {
        checkLevelIsValid(level);
        checkClassExists(classId);

        return classFeatureRepository
                .findAvailableClassFeaturesByClassIdAndLevel(classId, level)
                .stream()
                .map(ClassFeatureMapper::toClassFeatureResponse)
                .toList();
    }

    @Override
    public ClassFeatureResponse getClassFeatureById(
            UUID classId,
            UUID classFeatureId,
            Integer level
    ) {
        checkLevelIsValid(level);
        checkClassExists(classId);

        return classFeatureRepository
                .findAvailableClassFeatureByIdAndClassIdAndLevel(
                        classFeatureId,
                        classId,
                        level
                )
                .map(ClassFeatureMapper::toClassFeatureResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Class feature not found with id: "
                                + classFeatureId
                                + " for class id: "
                                + classId
                                + " and level: "
                                + level
                ));
    }

    @Override
    public List<ClassArchetypeResponse> getAllArchetypes(UUID classId) {
        checkClassExists(classId);

        return classArchetypeRepository
                .findAllByCharacterClassId(classId)
                .stream()
                .map(ClassArchetypeMapper::toClassArchetypeResponse)
                .toList();
    }

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
                ));    }

    @Override
    public List<ClassArchetypeFeatureResponse> getAllFeatures(
            UUID classId,
            UUID classArchetypeId
    ) {
        checkArchetypeBelongsToClass(classId, classArchetypeId);

        return classArchetypeFeatureRepository
                .findAllByClassArchetypeIdAndClassArchetypeCharacterClassId(
                        classArchetypeId,
                        classId
                )
                .stream()
                .map(ClassArchetypeFeatureMapper::toClassArchetypeFeatureResponse)
                .toList();
    }

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

    private void checkClassExists(UUID classId) {
        if (!characterClassRepository.existsById(classId)) {
            throw new EntityNotFoundException(
                    "Class not found with id: " + classId
            );
        }
    }

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

    private void checkLevelIsValid(Integer level) {
        if (level == null || level <= 0) {
            throw new IllegalArgumentException(
                    "Level must be greater than 0"
            );
        }
    }
}