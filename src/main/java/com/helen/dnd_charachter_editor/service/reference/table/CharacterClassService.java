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
 * Service contract for character class service operations.
 */
public interface CharacterClassService {
    /**
     * Returns all classes.
     * @return result of the operation
     */
    List<CharacterClassResponse> getAllClasses();

    /**
     * Returns class response by id.
     * @param classId value used by this operation
     * @return result of the operation
     */
    CharacterClassResponse getClassResponseById(UUID classId);

    /**
     * Returns class by id.
     * @param classId value used by this operation
     * @return result of the operation
     */
    CharacterClass getClassById(UUID classId);

    /**
     * Returns all features.
     * @param classId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
     */
    List<ClassFeatureResponse> getAllFeatures(UUID classId, Integer level);

    /**
     * Returns class feature by id.
     * @param classId value used by this operation
     * @param classFeatureId value used by this operation
     * @return result of the operation
     */
    ClassFeatureResponse getClassFeatureById(
            UUID classId,
            UUID classFeatureId
    );

    /**
     * Returns all archetypes.
     * @param classId value used by this operation
     * @return result of the operation
     */
    List<ClassArchetypeResponse> getAllArchetypes(UUID classId);

    /**
     * Returns class archetype response by id.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @return result of the operation
     */
    ClassArchetypeResponse getClassArchetypeResponseById(
            UUID classId,
            UUID classArchetypeId
    );

    /**
     * Returns class archetype by id.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @return result of the operation
     */
    ClassArchetype getClassArchetypeById(
            UUID classId,
            UUID classArchetypeId
    );

    /**
     * Returns all features.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
     */
    List<ClassArchetypeFeatureResponse> getAllFeatures(
            UUID classId,
            UUID classArchetypeId,
            Integer level
    );

    /**
     * Returns archetype feature by id.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @param classArchetypeFeatureId value used by this operation
     * @return result of the operation
     */
    ClassArchetypeFeatureResponse getArchetypeFeatureById(
            UUID classId,
            UUID classArchetypeId,
            UUID classArchetypeFeatureId
    );

}
