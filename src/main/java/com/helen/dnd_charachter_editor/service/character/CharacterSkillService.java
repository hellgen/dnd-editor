package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSkillRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSkillResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service contract for character skill service operations.
 */
public interface CharacterSkillService {

    /**
     * Returns character skills.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    List<CharacterSkillResponse> getCharacterSkills(UUID characterId);

    /**
     * Updates character skill.
     * @param characterId value used by this operation
     * @param skillId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    CharacterSkillResponse updateCharacterSkill(
            UUID characterId,
            UUID skillId,
            SetCharacterSkillRequest request
    );
}
