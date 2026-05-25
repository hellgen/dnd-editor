package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.CharacterClassResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassFeatureResponse;
import java.util.List;
import java.util.UUID;

public interface CharacterClassService {
    List<CharacterClassResponse> getAllClasses();

    CharacterClassResponse getClassById(UUID classId);

    List<ClassFeatureResponse> getAllFeaturesByLevel(
            UUID classId,
            Integer level
    );

    ClassFeatureResponse getClassFeatureById(
            UUID classId,
            UUID classFeatureId,
            Integer level
    );

    List<ClassArchetypeResponse> getAllArchetypes(UUID classId);

    ClassArchetypeResponse getClassArchetypeById(
            UUID classId,
            UUID classArchetypeId
    );

    List<ClassArchetypeFeatureResponse> getAllFeatures(
            UUID classId,
            UUID classArchetypeId
    );

    ClassArchetypeFeatureResponse getArchetypeFeatureById(
            UUID classId,
            UUID classArchetypeId,
            UUID classArchetypeFeatureId
    );

}
