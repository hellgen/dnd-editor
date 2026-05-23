package com.helen.dnd_charachter_editor.dto.response.referencetable;

import java.util.UUID;

public record CharacterClassResponse(
        UUID id,
        String name,
        Integer hitDie,
        String primaryAbility
) {
}