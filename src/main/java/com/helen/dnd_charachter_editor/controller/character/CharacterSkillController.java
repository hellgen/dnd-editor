package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSkillRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSkillResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/characters/{characterId}/skills")
@RequiredArgsConstructor
public class CharacterSkillController {

    private final CharacterSkillService characterSkillService;

    @GetMapping
    public List<CharacterSkillResponse> getCharacterSkills(
            @PathVariable UUID characterId
    ) {
        return characterSkillService.getCharacterSkills(characterId);
    }

    @PutMapping("/{skillId}")
    public CharacterSkillResponse updateCharacterSkill(
            @PathVariable UUID characterId,
            @PathVariable UUID skillId,
            @Valid @RequestBody SetCharacterSkillRequest request
    ) {
        return characterSkillService.updateCharacterSkill(characterId, skillId, request);
    }
}
