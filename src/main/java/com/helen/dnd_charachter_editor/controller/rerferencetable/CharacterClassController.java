package com.helen.dnd_charachter_editor.controller.rerferencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.CharacterClassResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.service.referencetable.CharacterClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
public class CharacterClassController {

    private final CharacterClassService characterClassService;

    @GetMapping
    public List<CharacterClassResponse> getAllClasses() {
        return characterClassService.getAllClasses();
    }

    @GetMapping("/{classId}")
    public CharacterClassResponse getClassById(
            @PathVariable UUID classId
    ) {
        return characterClassService.getClassById(classId);
    }

    @GetMapping("/{classId}/features")
    public List<ClassFeatureResponse> getAllFeaturesByLevel(
            @PathVariable UUID classId,
            @RequestParam Integer level
    ) {
        return characterClassService.getAllFeaturesByLevel(
                classId,
                level
        );
    }

    @GetMapping("/{classId}/features/{classFeatureId}")
    public ClassFeatureResponse getClassFeatureById(
            @PathVariable UUID classId,
            @PathVariable UUID classFeatureId,
            @RequestParam Integer level
    ) {
        return characterClassService.getClassFeatureById(
                classId,
                classFeatureId,
                level
        );
    }

    @GetMapping("/{classId}/class-archetypes")
    public List<ClassArchetypeResponse> getAllArchetypes(
            @PathVariable UUID classId
    ) {
        return characterClassService.getAllArchetypes(classId);
    }

    @GetMapping("/{classId}/class-archetypes/{classArchetypeId}")
    public ClassArchetypeResponse getClassArchetypeById(
            @PathVariable UUID classId,
            @PathVariable UUID classArchetypeId
    ) {
        return characterClassService.getClassArchetypeById(
                classId,
                classArchetypeId
        );
    }

    @GetMapping("/{classId}/class-archetypes/{classArchetypeId}/features")
    public List<ClassArchetypeFeatureResponse> getAllFeatures(
            @PathVariable UUID classId,
            @PathVariable UUID classArchetypeId
    ) {
        return characterClassService.getAllFeatures(
                classId,
                classArchetypeId
        );
    }

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