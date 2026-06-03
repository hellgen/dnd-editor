package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterSpellRequest;
import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassArchetypeRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterRaceRequest;
import com.helen.dnd_charachter_editor.dto.request.character.UpdateCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.WalletUpdateRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterInventoryResponse;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.dto.response.character.WalletResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер REST API для обработки запросов `CharacterController`.
 */
@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    /**
     * Создаёт данные для запрошенной операции.
     * @param createCharacterRequest параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PostMapping
    private CharacterResponse createCharacter(@RequestBody CreateCharacterRequest createCharacterRequest) {
        return characterService.createCharacter(createCharacterRequest);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{characterId}")
    private CharacterResponse getCharacter(@PathVariable UUID characterId) {
        return characterService.getCharacter(characterId);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param createCharacterRequest параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PutMapping("/{characterId}")
    private CharacterResponse updateCharacter(
            @PathVariable UUID characterId,
            @RequestBody CreateCharacterRequest createCharacterRequest
    ) {
        return characterService.updateCharacter(characterId, createCharacterRequest);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PutMapping("/{characterId}/level")
    private CharacterResponse updateCharacterLevel(
            @PathVariable UUID characterId,
            @RequestParam Integer level
    ) {
        return characterService.updateCharacterLevel(characterId, level);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param maxHealth параметр, используемый при выполнении операции
     * @param currentHealth параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PutMapping("/{characterId}/health")
    private CharacterResponse updateCharacterHealth(
            @PathVariable UUID characterId,
            @RequestParam Integer maxHealth,
            @RequestParam Integer currentHealth
    ) {
        return characterService.updateCharacterHealth(characterId, maxHealth, currentHealth);
    }

    /**
     * Применяет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PostMapping("/{characterId}/class")
    public CharacterResponse applyCharacterClass(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassRequest request
    ) {
        return characterService.applyCharacterClass(characterId, request);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PutMapping("/{characterId}/class")
    public CharacterResponse updateCharacterClass(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassRequest request
    ) {
        return characterService.updateCharacterClass(characterId, request);
    }

    /**
     * Применяет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PostMapping("/{characterId}/class-archetype")
    public CharacterResponse applyCharacterClassArchetype(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassArchetypeRequest request
    ) {
        return characterService.applyCharacterClassArchetype(characterId, request);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PutMapping("/{characterId}/class-archetype")
    public CharacterResponse updateCharacterClassArchetype(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassArchetypeRequest request
    ) {
        return characterService.updateCharacterClassArchetype(characterId, request);
    }

    /**
     * Применяет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PostMapping("/{characterId}/race")
    public CharacterResponse applyCharacterRace(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterRaceRequest request
    ) {
        return characterService.applyCharacterRace(characterId, request);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PutMapping("/{characterId}/race")
    public CharacterResponse updateCharacterRace(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterRaceRequest request
    ) {
        return characterService.updateCharacterRace(characterId, request);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{characterId}/spells")
    public List<SpellResponse> getCharacterSpells(@PathVariable UUID characterId) {
        return characterService.getCharacterSpells(characterId);
    }

    /**
     * Добавляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PostMapping("/{characterId}/spells")
    public SpellResponse addCharacterSpell(
            @PathVariable UUID characterId,
            @Valid @RequestBody AddCharacterSpellRequest request
    ) {
        return characterService.addCharacterSpell(characterId, request);
    }

    /**
     * Удаляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param spellId параметр, используемый при выполнении операции
     */
    @DeleteMapping("/{characterId}/spells/{spellId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacterSpell(
            @PathVariable UUID characterId,
            @PathVariable UUID spellId
    ) {
        characterService.deleteCharacterSpell(characterId, spellId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{characterId}/inventory")
    public List<CharacterInventoryResponse> getCharacterInventory(@PathVariable UUID characterId) {
        return characterService.getCharacterInventory(characterId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param itemName параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{characterId}/inventory/item")
    public CharacterInventoryResponse getCharacterInventoryItem(
            @PathVariable UUID characterId,
            @RequestParam String itemName
    ) {
        return characterService.getCharacterInventoryItem(characterId, itemName);
    }

    /**
     * Добавляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PostMapping("/{characterId}/inventory/item")
    public CharacterInventoryResponse addCharacterInventoryItem(
            @PathVariable UUID characterId,
            @Valid @RequestBody AddCharacterInventoryRequest request
    ) {
        return characterService.addCharacterInventoryItem(characterId, request);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param requests параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PutMapping("/{characterId}/inventory/items")
    public List<CharacterInventoryResponse> updateCharacterInventoryItems(
            @PathVariable UUID characterId,
            @Valid @RequestBody List<@Valid UpdateCharacterInventoryRequest> requests
    ) {
        return characterService.updateCharacterInventoryItems(characterId, requests);
    }

    /**
     * Удаляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param itemName параметр, используемый при выполнении операции
     */
    @DeleteMapping("/{characterId}/inventory/item")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacterInventoryItem(
            @PathVariable UUID characterId,
            @RequestParam String itemName
    ) {
        characterService.deleteCharacterInventoryItem(characterId, itemName);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{characterId}/wallet")
    public WalletResponse getCharacterWallet(@PathVariable UUID characterId) {
        return characterService.getCharacterWallet(characterId);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PutMapping("/{characterId}/wallet")
    public WalletResponse updateCharacterWallet(
            @PathVariable UUID characterId,
            @Valid @RequestBody WalletUpdateRequest request
    ) {
        return characterService.updateCharacterWallet(characterId, request);
    }

    /**
     * Удаляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteCharacter(@RequestParam UUID characterId) {
        characterService.deleteCharacter(characterId);
    }
}
