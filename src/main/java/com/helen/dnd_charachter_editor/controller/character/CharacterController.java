package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassArchetypeRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterRaceRequest;
import com.helen.dnd_charachter_editor.dto.request.character.UpdateCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterInventoryResponse;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
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
 * REST controller that exposes character controller endpoints.
 */
@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    /**
     * Creates character.
     * @param createCharacterRequest value used by this operation
     * @return result of the operation
     */
    @PostMapping
    private CharacterResponse createCharacter(@RequestBody CreateCharacterRequest createCharacterRequest) {
        return characterService.createCharacter(createCharacterRequest);
    }

    /**
     * Returns character.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{characterId}")
    private CharacterResponse getCharacter(@PathVariable UUID characterId) {
        return characterService.getCharacter(characterId);
    }

    /**
     * Updates character.
     * @param characterId value used by this operation
     * @param createCharacterRequest value used by this operation
     * @return result of the operation
     */
    @PutMapping("/{characterId}")
    private CharacterResponse updateCharacter(
            @PathVariable UUID characterId,
            @RequestBody CreateCharacterRequest createCharacterRequest
    ) {
        return characterService.updateCharacter(characterId, createCharacterRequest);
    }

    /**
     * Updates character level.
     * @param characterId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
     */
    @PutMapping("/{characterId}/level")
    private CharacterResponse updateCharacterLevel(
            @PathVariable UUID characterId,
            @RequestParam Integer level
    ) {
        return characterService.updateCharacterLevel(characterId, level);
    }

    /**
     * Updates character health.
     * @param characterId value used by this operation
     * @param maxHealth value used by this operation
     * @param currentHealth value used by this operation
     * @return result of the operation
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
     * Applies character class.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @PostMapping("/{characterId}/class")
    public CharacterResponse applyCharacterClass(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassRequest request
    ) {
        return characterService.applyCharacterClass(characterId, request);
    }

    /**
     * Updates character class.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @PutMapping("/{characterId}/class")
    public CharacterResponse updateCharacterClass(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassRequest request
    ) {
        return characterService.updateCharacterClass(characterId, request);
    }

    /**
     * Applies character class archetype.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @PostMapping("/{characterId}/class-archetype")
    public CharacterResponse applyCharacterClassArchetype(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassArchetypeRequest request
    ) {
        return characterService.applyCharacterClassArchetype(characterId, request);
    }

    /**
     * Updates character class archetype.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @PutMapping("/{characterId}/class-archetype")
    public CharacterResponse updateCharacterClassArchetype(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterClassArchetypeRequest request
    ) {
        return characterService.updateCharacterClassArchetype(characterId, request);
    }

    /**
     * Applies character race.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @PostMapping("/{characterId}/race")
    public CharacterResponse applyCharacterRace(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterRaceRequest request
    ) {
        return characterService.applyCharacterRace(characterId, request);
    }

    /**
     * Updates character race.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @PutMapping("/{characterId}/race")
    public CharacterResponse updateCharacterRace(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterRaceRequest request
    ) {
        return characterService.updateCharacterRace(characterId, request);
    }

    /**
     * Returns character inventory.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{characterId}/inventory")
    public List<CharacterInventoryResponse> getCharacterInventory(@PathVariable UUID characterId) {
        return characterService.getCharacterInventory(characterId);
    }

    /**
     * Returns one character inventory item by item name.
     * @param characterId value used by this operation
     * @param itemName value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{characterId}/inventory/item")
    public CharacterInventoryResponse getCharacterInventoryItem(
            @PathVariable UUID characterId,
            @RequestParam String itemName
    ) {
        return characterService.getCharacterInventoryItem(characterId, itemName);
    }

    /**
     * Adds item to character inventory.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @PostMapping("/{characterId}/inventory/item")
    public CharacterInventoryResponse addCharacterInventoryItem(
            @PathVariable UUID characterId,
            @Valid @RequestBody AddCharacterInventoryRequest request
    ) {
        return characterService.addCharacterInventoryItem(characterId, request);
    }

    /**
     * Updates character inventory items.
     * @param characterId value used by this operation
     * @param requests value used by this operation
     * @return result of the operation
     */
    @PutMapping("/{characterId}/inventory/items")
    public List<CharacterInventoryResponse> updateCharacterInventoryItems(
            @PathVariable UUID characterId,
            @Valid @RequestBody List<@Valid UpdateCharacterInventoryRequest> requests
    ) {
        return characterService.updateCharacterInventoryItems(characterId, requests);
    }

    /**
     * Deletes one character inventory item by item name.
     * @param characterId value used by this operation
     * @param itemName value used by this operation
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
     * Deletes character.
     * @param characterId value used by this operation
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteCharacter(@RequestParam UUID characterId) {
        characterService.deleteCharacter(characterId);
    }
}
