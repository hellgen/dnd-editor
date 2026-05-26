package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.Skill;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;

import java.util.List;
import java.util.Map;

public class CharacterResponseMapper {

    public static CharacterResponse toResponse(
            UserCharacter character,
            CreateCharacterRequest request,
            List<Ability> abilities,
            List<Skill> skills,
            List<Spell> spells,
            int savingThrowsCount
    ) {
        Map<String, Integer> wallet = request.wallet() == null ? Map.of() : request.wallet();

        return new CharacterResponse(
                character.getName(),
                character.getRace().getName(),
                character.getSubrace() != null ? character.getSubrace().getName() : null,
                character.getClassField().getName(),
                character.getClassArchetype() != null ? character.getClassArchetype().getName() : null,
                character.getLevel(),
                character.getMaxHealth(),
                character.getCurrentHealth(),
                character.getAppearance(),
                character.getArmorClass(),
                request.inventory(),
                wallet,
                abilities.stream().map(Ability::getName).toList(),
                skills.stream().map(Skill::getName).toList(),
                spells.stream().map(Spell::getSpellName).toList(),
                savingThrowsCount,
                character.getCreatedAt(),
                character.getUpdatedAt()
        );
    }
}
