package com.helen.dnd_charachter_editor.service.referencetable.impl;

import com.helen.dnd_charachter_editor.dto.response.referencetable.CharacterClassResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.mapper.referencetable.CharacterClassMapper;
import com.helen.dnd_charachter_editor.mapper.referencetable.ClassArchetypeFeatureMapper;
import com.helen.dnd_charachter_editor.mapper.referencetable.ClassArchetypeMapper;
import com.helen.dnd_charachter_editor.mapper.referencetable.ClassFeatureMapper;
import com.helen.dnd_charachter_editor.repository.referncetable.CharacterClassRepository;
import com.helen.dnd_charachter_editor.repository.referncetable.ClassArchetypeFeatureRepository;
import com.helen.dnd_charachter_editor.repository.referncetable.ClassArchetypeRepository;
import com.helen.dnd_charachter_editor.repository.referncetable.ClassFeatureRepository;
import com.helen.dnd_charachter_editor.service.referencetable.CharacterClassService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CharacterClassImpl implements CharacterClassService {

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
    public CharacterClassResponse getClassById(UUID classId) {
        return characterClassRepository.findById(classId)
                .map(CharacterClassMapper::toCharacterClassResponse)
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
    public ClassArchetypeResponse getClassArchetypeById(
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