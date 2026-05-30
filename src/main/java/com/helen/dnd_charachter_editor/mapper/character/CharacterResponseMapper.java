package com.helen.dnd_charachter_editor.mapper.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import com.helen.dnd_charachter_editor.entity.character.CharacterSkill;
import com.helen.dnd_charachter_editor.entity.character.CharacterSpell;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.Skill;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;

import java.util.List;
import java.util.UUID;

public class CharacterResponseMapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final TypeReference<List<String>> STRING_LIST_TYPE = new TypeReference<>() {
    };

    public static CharacterResponse toResponse(
            UserCharacter character,
            CreateCharacterRequest request,
            List<Ability> abilities,
            List<Skill> skills,
            List<Spell> spells,
            int savingThrowsCount
    ) {
        return new CharacterResponse(
                character.getName(),
                character.getRace().getName(),
                character.getSubrace() != null ? character.getSubrace().getName() : null,
                character.getClassField().getClassName(),
                character.getClassArchetype() != null ? character.getClassArchetype().getName() : null,
                character.getLevel(),
                character.getMaxHealth(),
                character.getCurrentHealth(),
                character.getAppearance(),
                character.getArmorClass(),
                request.inventory(),

                character.getPlatinum(),
                character.getGold(),
                character.getElectrum(),
                character.getSilver(),
                character.getCopper(),

                abilities.stream().map(Ability::getName).toList(),
                skills.stream().map(Skill::getName).toList(),
                spells.stream().map(Spell::getSpellName).toList(),
                savingThrowsCount,
                character.getCreatedAt(),
                character.getUpdatedAt()
        );
    }

    public static CharacterResponse toResponse(
            UserCharacter character,
            List<CharacterAbility> abilities,
            List<CharacterSkill> skills,
            List<CharacterSpell> spells,
            List<CharacterSavingThrow> savingThrows
    ) {
        return new CharacterResponse(
                character.getName(),
                character.getRace().getName(),
                character.getSubrace() != null ? character.getSubrace().getName() : null,
                character.getClassField().getClassName(),
                character.getClassArchetype() != null ? character.getClassArchetype().getName() : null,
                character.getLevel(),
                character.getMaxHealth(),
                character.getCurrentHealth(),
                character.getAppearance(),
                character.getArmorClass(),
                deserializeInventory(character.getInventory()),

                character.getPlatinum(),
                character.getGold(),
                character.getElectrum(),
                character.getSilver(),
                character.getCopper(),

                abilities.stream().map(ability -> ability.getAbility().getName()).toList(),
                skills.stream()
                        .filter(skill -> skill.getProficiencyLevel() > 0)
                        .map(skill -> skill.getSkill().getName())
                        .toList(),
                spells.stream().map(spell -> spell.getSpell().getSpellName()).toList(),
                (int) savingThrows.stream()
                        .filter(savingThrow -> savingThrow.getProficiencyLevel() > 0)
                        .count(),
                character.getCreatedAt(),
                character.getUpdatedAt()
        );
    }

    public static String serializeInventory(List<String> inventory) {
        return serializeList(inventory, "Unable to serialize character inventory");
    }

    public static String serializeIds(List<UUID> ids) {
        return serializeList(ids, "Unable to serialize character ids");
    }

    private static String serializeList(List<?> values, String errorMessage) {
        if (values == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    private static List<String> deserializeInventory(String inventory) {
        if (inventory == null || inventory.isBlank()) {
            return List.of();
        }

        try {
            return OBJECT_MAPPER.readValue(inventory, STRING_LIST_TYPE);
        } catch (JsonProcessingException e) {
            return List.of(inventory);
        }
    }
}
