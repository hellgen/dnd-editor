package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.CharacterClassResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller that exposes character class controller endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
public class CharacterClassController {

    private final CharacterClassService characterClassService;

    /**
     * Returns all classes.
     * @return result of the operation
     */
    @GetMapping
    public List<CharacterClassResponse> getAllClasses() {
        return characterClassService.getAllClasses();
    }

    /**
     * Returns class by id.
     * @param classId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{classId}")
    public CharacterClassResponse getClassById(
            @PathVariable UUID classId
    ) {
        return characterClassService.getClassResponseById(classId);
    }

    /**
     * Returns all features.
     * @param classId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{classId}/features")
    public List<ClassFeatureResponse> getAllFeatures(
            @PathVariable UUID classId,
            @RequestParam(required = false) Integer level
    ) {
        return characterClassService.getAllFeatures(classId, level);
    }

    /**
     * Returns class feature by id.
     * @param classId value used by this operation
     * @param classFeatureId value used by this operation
     * @return result of the operation
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
     * Returns all archetypes.
     * @param classId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{classId}/class-archetypes")
    public List<ClassArchetypeResponse> getAllArchetypes(
            @PathVariable UUID classId
    ) {
        return characterClassService.getAllArchetypes(classId);
    }

    /**
     * Returns class archetype by id.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @return result of the operation
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
     * Returns all features.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
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
     * Returns archetype feature by id.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @param classArchetypeFeatureId value used by this operation
     * @return result of the operation
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
