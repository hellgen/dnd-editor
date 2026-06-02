package com.helen.dnd_charachter_editor.dto.response.reference.table;

import java.util.UUID;

public record CharacterClassResponse(
        UUID id,
        String className,
        String classDescription,
        Boolean isSpellcaster,
        Integer spellcastingStartLevel
) {
}