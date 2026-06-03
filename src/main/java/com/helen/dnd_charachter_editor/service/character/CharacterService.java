package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterSpellRequest;
import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassArchetypeRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterRaceRequest;
import com.helen.dnd_charachter_editor.dto.request.character.WalletUpdateRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterInventoryResponse;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.dto.response.character.WalletResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса `CharacterService`.
 */
public interface CharacterService {
    /**
     * Создаёт данные для запрошенной операции.
     * @param createCharacterRequest параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse createCharacter(CreateCharacterRequest createCharacterRequest);

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse getCharacter(UUID characterId);

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param createCharacterRequest параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse updateCharacter(UUID characterId, CreateCharacterRequest createCharacterRequest);

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse updateCharacterLevel(UUID characterId, Integer level);

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param maxHealth параметр, используемый при выполнении операции
     * @param currentHealth параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse updateCharacterHealth(UUID characterId, Integer maxHealth, Integer currentHealth);

    /**
     * Применяет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse applyCharacterRace(UUID characterId, SetCharacterRaceRequest request);

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse updateCharacterRace(UUID characterId, SetCharacterRaceRequest request);

    /**
     * Применяет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse applyCharacterClass(UUID characterId, SetCharacterClassRequest request);

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse updateCharacterClass(UUID characterId, SetCharacterClassRequest request);

    /**
     * Применяет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse applyCharacterClassArchetype(UUID characterId, SetCharacterClassArchetypeRequest request);

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterResponse updateCharacterClassArchetype(UUID characterId, SetCharacterClassArchetypeRequest request);

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<SpellResponse> getCharacterSpells(UUID characterId);

    /**
     * Добавляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    SpellResponse addCharacterSpell(UUID characterId, AddCharacterSpellRequest request);

    /**
     * Удаляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param spellId параметр, используемый при выполнении операции
     */
    void deleteCharacterSpell(UUID characterId, UUID spellId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<CharacterInventoryResponse> getCharacterInventory(UUID characterId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param itemName параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterInventoryResponse getCharacterInventoryItem(UUID characterId, String itemName);

    /**
     * Добавляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    CharacterInventoryResponse addCharacterInventoryItem(UUID characterId, AddCharacterInventoryRequest request);

    /**
     * Обновляет список предметов в инвентаре персонажа.
     * @param characterId параметр, используемый при выполнении операции
     * @param items список названий предметов инвентаря
     * @return обновленный список названий предметов инвентаря
     */
    List<String> updateCharacterInventoryItems(
            UUID characterId,
            List<String> items
    );

    /**
     * Удаляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param itemName параметр, используемый при выполнении операции
     */
    void deleteCharacterInventoryItem(UUID characterId, String itemName);

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    WalletResponse getCharacterWallet(UUID characterId);

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    WalletResponse updateCharacterWallet(UUID characterId, WalletUpdateRequest request);

    /**
     * Удаляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     */
    void deleteCharacter(UUID characterId);
}
