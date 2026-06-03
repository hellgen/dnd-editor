package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterSpellRequest;
import com.helen.dnd_charachter_editor.dto.request.character.UpdateCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.WalletUpdateRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterInventoryResponse;
import com.helen.dnd_charachter_editor.dto.response.character.WalletResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.CharacterSpell;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;
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
import com.helen.dnd_charachter_editor.service.reference.table.SpellService;
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
 * Реализация сервиса `DefaultCharacterInventoryServiceTest`.
 */
class DefaultCharacterInventoryServiceTest {

    private final AuthService authService = mock(AuthService.class);
    private final RaceService raceService = mock(RaceService.class);
    private final CharacterClassService characterClassService = mock(CharacterClassService.class);
    private final CharacterAbilityService characterAbilityService = mock(CharacterAbilityService.class);
    private final SpellService spellService = mock(SpellService.class);
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
            spellService,
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
     * Возвращает данные для запрошенной операции.
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
     * Добавляет данные для запрошенной операции.
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
     * Обновляет данные для запрошенной операции.
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
     * Удаляет данные для запрошенной операции.
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
     * Возвращает данные для запрошенной операции.
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
     * Возвращает данные для запрошенной операции.
     */
    @Test
    void getCharacterSpellsReturnsCharacterSpellResponses() {
        TestData data = prepareCharacter(null);
        Spell spell = spell(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Волшебная стрела");
        CharacterSpell characterSpell = characterSpell(data.character(), spell);
        when(characterSpellRepository.findAllByCharacterId(data.characterId())).thenReturn(List.of(characterSpell));

        var response = service.getCharacterSpells(data.characterId());

        assertEquals(1, response.size());
        assertEquals("Волшебная стрела", response.getFirst().spellName());
    }

    /**
     * Добавляет данные для запрошенной операции.
     */
    @Test
    void addCharacterSpellAddsAvailableSpell() {
        TestData data = prepareCharacter(null);
        CharacterClass characterClass = characterClass("Волшебник", true);
        data.character().setClassField(characterClass);
        Spell spell = spell(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Волшебная стрела");
        AddCharacterSpellRequest request = new AddCharacterSpellRequest(spell.getId());
        when(spellService.getSpell(spell.getId())).thenReturn(spell);
        when(spellService.isSpellAvailableForClass(characterClass, spell)).thenReturn(true);
        when(characterSpellRepository.existsByCharacterIdAndSpellId(data.characterId(), spell.getId())).thenReturn(false);
        when(characterSpellRepository.save(any(CharacterSpell.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(characterRepository.save(any(UserCharacter.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = service.addCharacterSpell(data.characterId(), request);

        assertEquals("Волшебная стрела", response.spellName());
        verify(characterSpellRepository).save(any(CharacterSpell.class));
        verify(characterRepository).save(data.character());
    }

    /**
     * Добавляет данные для запрошенной операции.
     */
    @Test
    void addCharacterSpellThrowsWhenSpellUnavailableForClass() {
        TestData data = prepareCharacter(null);
        CharacterClass characterClass = characterClass("Воин", false);
        data.character().setClassField(characterClass);
        Spell spell = spell(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Волшебная стрела");
        when(spellService.getSpell(spell.getId())).thenReturn(spell);
        when(spellService.isSpellAvailableForClass(characterClass, spell)).thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addCharacterSpell(data.characterId(), new AddCharacterSpellRequest(spell.getId()))
        );
    }

    /**
     * Удаляет данные для запрошенной операции.
     */
    @Test
    void deleteCharacterSpellRemovesCharacterSpell() {
        TestData data = prepareCharacter(null);
        Spell spell = spell(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Волшебная стрела");
        data.character().setSpells("[\"33333333-3333-3333-3333-333333333333\"]");
        CharacterSpell characterSpell = characterSpell(data.character(), spell);
        when(characterSpellRepository.findByCharacterIdAndSpellId(data.characterId(), spell.getId()))
                .thenReturn(Optional.of(characterSpell));
        when(characterRepository.save(any(UserCharacter.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.deleteCharacterSpell(data.characterId(), spell.getId());

        assertEquals("[]", data.character().getSpells());
        verify(characterSpellRepository).delete(characterSpell);
        verify(characterRepository).save(data.character());
    }

    /**
     * Возвращает данные для запрошенной операции.
     */
    @Test
    void getCharacterWalletReturnsCharacterCoins() {
        TestData data = prepareCharacter(null);
        data.character().setCopper(10);
        data.character().setSilver(5);
        data.character().setElectrum(1);
        data.character().setGold(2);
        data.character().setPlatinum(0);

        WalletResponse response = service.getCharacterWallet(data.characterId());

        assertEquals(data.characterId(), response.characterId());
        assertEquals(10, response.copper());
        assertEquals(5, response.silver());
        assertEquals(1, response.electrum());
        assertEquals(2, response.gold());
        assertEquals(0, response.platinum());
    }

    /**
     * Обновляет данные для запрошенной операции.
     */
    @Test
    void updateCharacterWalletUpdatesOnlyProvidedCoins() {
        TestData data = prepareCharacter(null);
        data.character().setCopper(1);
        data.character().setSilver(2);
        data.character().setElectrum(3);
        data.character().setGold(4);
        data.character().setPlatinum(5);
        WalletUpdateRequest request = new WalletUpdateRequest(10, null, null, 20, null);
        when(characterRepository.save(any(UserCharacter.class))).thenAnswer(invocation -> invocation.getArgument(0));

        WalletResponse response = service.updateCharacterWallet(data.characterId(), request);

        assertEquals(10, response.copper());
        assertEquals(2, response.silver());
        assertEquals(3, response.electrum());
        assertEquals(20, response.gold());
        assertEquals(5, response.platinum());
        verify(characterRepository).save(data.character());
    }

    /**
     * Обновляет данные для запрошенной операции.
     */
    @Test
    void updateCharacterWalletThrowsWhenCoinValueIsNegative() {
        TestData data = prepareCharacter(null);
        WalletUpdateRequest request = new WalletUpdateRequest(-1, null, null, null, null);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateCharacterWallet(data.characterId(), request)
        );
    }


    /**
     * Выполняет запрошенную операцию.
     * @param spellId параметр, используемый при выполнении операции
     * @param spellName параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Spell spell(UUID spellId, String spellName) {
        Spell spell = new Spell();
        spell.setId(spellId);
        spell.setSpellName(spellName);
        spell.setSpellLevel(1);
        spell.setSpellSchool("Воплощение");
        spell.setCastingTime("1 действие");
        spell.setSpellRange("120 футов");
        spell.setComponents("В, С");
        spell.setDuration("Мгновенная");
        spell.setSpellDescription("Описание");
        return spell;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param className параметр, используемый при выполнении операции
     * @param isSpellcaster параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterClass characterClass(String className, Boolean isSpellcaster) {
        CharacterClass characterClass = new CharacterClass();
        characterClass.setId(UUID.fromString("44444444-4444-4444-4444-444444444444"));
        characterClass.setClassName(className);
        characterClass.setIsSpellcaster(isSpellcaster);
        return characterClass;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     * @param spell параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterSpell characterSpell(UserCharacter character, Spell spell) {
        CharacterSpell characterSpell = new CharacterSpell();
        characterSpell.setId(UUID.fromString("55555555-5555-5555-5555-555555555555"));
        characterSpell.setCharacter(character);
        characterSpell.setSpell(spell);
        characterSpell.setIsPrepared(false);
        return characterSpell;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param inventory параметр, используемый при выполнении операции
     * @return результат выполнения операции
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
     * Реализация сервиса `TestData`.
     */
    private record TestData(
            UUID characterId,
            UserCharacter character
    ) {
    }
}
