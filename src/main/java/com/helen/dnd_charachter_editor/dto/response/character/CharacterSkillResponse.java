package com.helen.dnd_charachter_editor.dto.response.character;

import java.util.UUID;

public record CharacterSkillResponse(
        UUID id,
        UUID characterId,
        UUID skillId,
        String skillName,
        String abilityCode,
        Integer abilityModifier,
        Integer proficiencyLevel,
        Integer proficiencyBonus,
        Integer totalModifier
) {
}
