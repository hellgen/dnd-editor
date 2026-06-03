package com.helen.dnd_charachter_editor.mapper.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterInventoryResponse;
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

/**
 * Mapper that converts character response mapper values between layers.
 */
public class CharacterResponseMapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final TypeReference<List<String>> STRING_LIST_TYPE = new TypeReference<>() {
    };
    private static final TypeReference<List<UUID>> UUID_LIST_TYPE = new TypeReference<>() {
    };
    private static final TypeReference<List<CharacterInventoryResponse>> INVENTORY_ITEM_LIST_TYPE = new TypeReference<>() {
    };

    /**
     * Converts response.
     * @param character value used by this operation
     * @param request value used by this operation
     * @param abilities value used by this operation
     * @param skills value used by this operation
     * @param spells value used by this operation
     * @param savingThrowsCount value used by this operation
     * @return result of the operation
     */
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

    /**
     * Converts response.
     * @param character value used by this operation
     * @param abilities value used by this operation
     * @param skills value used by this operation
     * @param spells value used by this operation
     * @param savingThrows value used by this operation
     * @return result of the operation
     */
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

    /**
     * Converts response.
     * @param character value used by this operation
     * @param abilities value used by this operation
     * @param skills value used by this operation
     * @param spells value used by this operation
     * @param savingThrowsCount value used by this operation
     * @return result of the operation
     */
    public static CharacterResponse toResponse(
            UserCharacter character,
            List<Ability> abilities,
            List<CharacterSkill> skills,
            List<Spell> spells,
            Integer savingThrowsCount
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

                abilities.stream().map(Ability::getName).toList(),
                skills.stream()
                        .filter(skill -> skill.getProficiencyLevel() > 0)
                        .map(skill -> skill.getSkill().getName())
                        .toList(),
                spells.stream().map(Spell::getSpellName).toList(),
                savingThrowsCount != null ? savingThrowsCount : 0,
                character.getCreatedAt(),
                character.getUpdatedAt()
        );
    }

    /**
     * Executes the serialize inventory operation.
     * @param inventory value used by this operation
     * @return result of the operation
     */
    public static String serializeInventory(List<String> inventory) {
        return serializeList(inventory, "Unable to serialize character inventory");
    }

    /**
     * Executes the serialize inventory items operation.
     * @param inventoryItems value used by this operation
     * @return result of the operation
     */
    public static String serializeInventoryItems(List<CharacterInventoryResponse> inventoryItems) {
        return serializeList(inventoryItems, "Unable to serialize character inventory items");
    }

    /**
     * Executes the serialize ids operation.
     * @param ids value used by this operation
     * @return result of the operation
     */
    public static String serializeIds(List<UUID> ids) {
        return serializeList(ids, "Unable to serialize character ids");
    }

    /**
     * Executes the serialize list operation.
     * @param values value used by this operation
     * @param errorMessage value used by this operation
     * @return result of the operation
     */
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

    /**
     * Executes the deserialize ids operation.
     * @param ids value used by this operation
     * @return result of the operation
     */
    public static List<UUID> deserializeIds(String ids) {
        if (ids == null || ids.isBlank()) {
            return List.of();
        }

        try {
            return OBJECT_MAPPER.readValue(ids, UUID_LIST_TYPE);
        } catch (JsonProcessingException e) {
            return List.of();
        }
    }

    /**
     * Executes the deserialize inventory operation.
     * @param inventory value used by this operation
     * @return result of the operation
     */
    public static List<String> deserializeInventory(String inventory) {
        if (inventory == null || inventory.isBlank()) {
            return List.of();
        }

        try {
            JsonNode root = OBJECT_MAPPER.readTree(inventory);
            if (root.isArray() && root.size() > 0 && root.get(0).isObject()) {
                return deserializeInventoryItems(inventory).stream()
                        .map(CharacterInventoryResponse::itemName)
                        .toList();
            }

            return OBJECT_MAPPER.readValue(inventory, STRING_LIST_TYPE);
        } catch (JsonProcessingException e) {
            return List.of(inventory);
        }
    }

    /**
     * Executes the deserialize inventory items operation.
     * @param inventory value used by this operation
     * @return result of the operation
     */
    public static List<CharacterInventoryResponse> deserializeInventoryItems(String inventory) {
        if (inventory == null || inventory.isBlank()) {
            return List.of();
        }

        try {
            JsonNode root = OBJECT_MAPPER.readTree(inventory);
            if (root.isArray() && (root.size() == 0 || root.get(0).isTextual())) {
                return OBJECT_MAPPER.readValue(inventory, STRING_LIST_TYPE).stream()
                        .map(itemName -> new CharacterInventoryResponse(
                                null,
                                null,
                                null,
                                itemName,
                                null,
                                1,
                                false,
                                null
                        ))
                        .toList();
            }

            return OBJECT_MAPPER.readValue(inventory, INVENTORY_ITEM_LIST_TYPE);
        } catch (JsonProcessingException e) {
            return List.of(new CharacterInventoryResponse(
                    null,
                    null,
                    null,
                    inventory,
                    null,
                    1,
                    false,
                    null
            ));
        }
    }
}
