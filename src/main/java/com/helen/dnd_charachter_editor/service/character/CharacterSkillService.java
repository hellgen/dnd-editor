package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSkillRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSkillResponse;

import java.util.List;
import java.util.UUID;

public interface CharacterSkillService {

    List<CharacterSkillResponse> getCharacterSkills(UUID characterId);

    CharacterSkillResponse updateCharacterSkill(
            UUID characterId,
            UUID skillId,
            SetCharacterSkillRequest request
    );
}
