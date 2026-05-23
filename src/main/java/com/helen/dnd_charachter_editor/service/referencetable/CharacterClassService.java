package com.helen.dnd_charachter_editor.service.referencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.CharacterClassResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassFeatureResponse;
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
