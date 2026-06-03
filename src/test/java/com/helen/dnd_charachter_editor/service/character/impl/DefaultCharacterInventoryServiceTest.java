package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.UpdateCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterInventoryResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.repository.character.CharacterAbilityRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterSavingThrowRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterSkillRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterSpellRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.AbilityRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.SkillRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.SpellRepository;
import com.helen.dnd_charachter_editor.service.auth.AuthService;
import com.helen.dnd_charachter_editor.service.character.CharacterAbilityService;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import com.helen.dnd_charachter_editor.service.reference.table.RaceService;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Default service implementation for default character inventory service test operations.
 */
class DefaultCharacterInventoryServiceTest {

    private final AuthService authService = mock(AuthService.class);
    private final RaceService raceService = mock(RaceService.class);
    private final CharacterClassService characterClassService = mock(CharacterClassService.class);
    private final CharacterAbilityService characterAbilityService = mock(CharacterAbilityService.class);
    private final CharacterRepository characterRepository = mock(CharacterRepository.class);
    private final CharacterAbilityRepository characterAbilityRepository = mock(CharacterAbilityRepository.class);
    private final CharacterSkillRepository characterSkillRepository = mock(CharacterSkillRepository.class);
    private final CharacterSpellRepository characterSpellRepository = mock(CharacterSpellRepository.class);
    private final CharacterSavingThrowRepository characterSavingThrowRepository = mock(CharacterSavingThrowRepository.class);
    private final SkillRepository skillRepository = mock(SkillRepository.class);
    private final SpellRepository spellRepository = mock(SpellRepository.class);
    private final AbilityRepository abilityRepository = mock(AbilityRepository.class);

    private final DefaultCharacterService service = new DefaultCharacterService(
            authService,
            raceService,
            characterClassService,
            characterAbilityService,
            characterRepository,
            characterAbilityRepository,
            characterSkillRepository,
            characterSpellRepository,
            characterSavingThrowRepository,
            skillRepository,
            spellRepository,
            abilityRepository
    );

    /**
     * Returns character inventory from old string list format.
     */
    @Test
    void getCharacterInventoryReadsLegacyStringInventory() {
        TestData data = prepareCharacter("[\"Longsword\"]");

        List<CharacterInventoryResponse> inventory = service.getCharacterInventory(data.characterId());

        assertEquals(1, inventory.size());
        assertEquals("Longsword", inventory.getFirst().itemName());
        assertEquals(1, inventory.getFirst().quantity());
    }

    /**
     * Adds character inventory item and persists structured inventory.
     */
    @Test
    void addCharacterInventoryItemPersistsNewItem() {
        TestData data = prepareCharacter(null);
        AddCharacterInventoryRequest request = new AddCharacterInventoryRequest(
                null,
                "Longsword",
                "A sharp blade",
                1,
                true,
                "Family heirloom"
        );
        when(characterRepository.save(any(UserCharacter.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CharacterInventoryResponse response = service.addCharacterInventoryItem(data.characterId(), request);

        assertEquals("Longsword", response.itemName());
        assertEquals(1, response.quantity());
        verify(characterRepository).save(data.character());
    }

    /**
     * Updates existing character inventory item.
     */
    @Test
    void updateCharacterInventoryItemsUpdatesExistingItem() {
        TestData data = prepareCharacter("[{\"itemName\":\"Longsword\",\"quantity\":1,\"isEquipped\":true}]");
        UpdateCharacterInventoryRequest request = new UpdateCharacterInventoryRequest(
                "Longsword",
                "Silver Longsword",
                null,
                2,
                false,
                "Polished"
        );
        when(characterRepository.save(any(UserCharacter.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<CharacterInventoryResponse> inventory = service.updateCharacterInventoryItems(data.characterId(), List.of(request));

        assertEquals("Silver Longsword", inventory.getFirst().itemName());
        assertEquals(2, inventory.getFirst().quantity());
        assertFalse(inventory.getFirst().isEquipped());
    }

    /**
     * Deletes character inventory item by name.
     */
    @Test
    void deleteCharacterInventoryItemRemovesExistingItem() {
        TestData data = prepareCharacter("[{\"itemName\":\"Longsword\",\"quantity\":1}]");
        when(characterRepository.save(any(UserCharacter.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.deleteCharacterInventoryItem(data.characterId(), "Longsword");

        assertEquals("[]", data.character().getInventory());
        verify(characterRepository).save(data.character());
    }

    /**
     * Throws not found when inventory item is absent.
     */
    @Test
    void getCharacterInventoryItemThrowsWhenItemMissing() {
        TestData data = prepareCharacter("[]");

        assertThrows(
                ResponseStatusException.class,
                () -> service.getCharacterInventoryItem(data.characterId(), "Longsword")
        );
    }

    /**
     * Prepares character test data.
     * @param inventory value used by this operation
     * @return result of the operation
     */
    private TestData prepareCharacter(String inventory) {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID characterId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        User user = new User();
        user.setId(userId);

        UserCharacter character = new UserCharacter();
        character.setId(characterId);
        character.setUser(user);
        character.setInventory(inventory);

        when(authService.getCurrentUser()).thenReturn(user);
        when(characterRepository.findByIdAndUser_Id(characterId, userId)).thenReturn(Optional.of(character));

        return new TestData(characterId, character);
    }

    /**
     * Default service implementation for test data operations.
     */
    private record TestData(
            UUID characterId,
            UserCharacter character
    ) {
    }
}
