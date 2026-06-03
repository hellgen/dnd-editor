package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterSpellRequest;
import com.helen.dnd_charachter_editor.dto.request.character.UpdateCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.WalletUpdateRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterInventoryResponse;
import com.helen.dnd_charachter_editor.dto.response.character.WalletResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import com.helen.dnd_charachter_editor.entity.character.CharacterSkill;
import com.helen.dnd_charachter_editor.entity.character.CharacterSpell;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.Skill;
import com.helen.dnd_charachter_editor.entity.reference.table.Spell;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
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
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
     * Создаёт данные для запрошенной операции.
     */
    @Test
    void createCharacterSavesSelectedSpellsToJoinTable() {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID characterId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID raceId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID subraceId = UUID.fromString("44444444-4444-4444-4444-444444444444");
        UUID classId = UUID.fromString("55555555-5555-5555-5555-555555555555");
        UUID classArchetypeId = UUID.fromString("66666666-6666-6666-6666-666666666666");
        UUID abilityId = UUID.fromString("77777777-7777-7777-7777-777777777777");
        UUID skillId = UUID.fromString("88888888-8888-8888-8888-888888888888");
        UUID spellId = UUID.fromString("99999999-9999-9999-9999-999999999999");
        User user = user(userId);
        Race race = race(raceId, "Эльф");
        Subrace subrace = subrace(subraceId, race, "Высший эльф");
        CharacterClass characterClass = characterClass(classId, "Волшебник", true);
        ClassArchetype classArchetype = classArchetype(classArchetypeId, characterClass, "Школа воплощения");
        Ability ability = ability(abilityId, "INT", "Интеллект");
        Skill skill = skill(skillId, "Магия", "INT");
        Spell spell = spell(spellId, "Волшебная стрела");
        CreateCharacterRequest request = new CreateCharacterRequest(
                "Гейл",
                raceId,
                subraceId,
                classId,
                classArchetypeId,
                1,
                8,
                8,
                "",
                12,
                List.of(),
                0,
                10,
                0,
                0,
                0,
                List.of(abilityId),
                List.of(skillId),
                List.of(spellId),
                1
        );
        when(authService.getCurrentUser()).thenReturn(user);
        when(raceService.getRace(raceId)).thenReturn(race);
        when(raceService.getSubrace(raceId, subraceId)).thenReturn(subrace);
        when(characterClassService.getClassById(classId)).thenReturn(characterClass);
        when(characterClassService.getClassArchetypeById(classId, classArchetypeId)).thenReturn(classArchetype);
        when(characterRepository.saveAndFlush(any(UserCharacter.class))).thenAnswer(invocation -> {
            UserCharacter character = invocation.getArgument(0);
            character.setId(characterId);
            return character;
        });
        when(characterAbilityRepository.findAllByIds(List.of(abilityId))).thenReturn(List.of(ability));
        when(skillRepository.findAll()).thenReturn(List.of(skill));
        when(spellRepository.findAllById(List.of(spellId))).thenReturn(List.of(spell));
        when(abilityRepository.findAll()).thenReturn(List.of(ability));
        when(characterAbilityRepository.saveAllAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(characterSkillRepository.saveAllAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(characterSpellRepository.saveAllAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(characterSavingThrowRepository.saveAllAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.createCharacter(request);

        ArgumentCaptor<Iterable<CharacterSpell>> captor = ArgumentCaptor.forClass(Iterable.class);
        verify(characterSpellRepository).saveAllAndFlush(captor.capture());
        CharacterSpell savedSpell = captor.getValue().iterator().next();
        assertEquals(characterId, savedSpell.getCharacter().getId());
        assertEquals(spellId, savedSpell.getSpell().getId());
    }


    /**
     * Обновляет данные для запрошенной операции.
     */
    @Test
    void updateCharacterClassDoesNotResetSelectionsWhenClassIsUnchanged() {
        TestData data = prepareCharacter("[]");
        UUID classId = UUID.fromString("44444444-4444-4444-4444-444444444444");
        CharacterClass characterClass = characterClass(classId, "Волшебник", true);
        Race race = race(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Эльф");
        data.character().setName("Гейл");
        data.character().setRace(race);
        data.character().setClassField(characterClass);
        data.character().setLevel(1);
        data.character().setMaxHealth(8);
        data.character().setCurrentHealth(8);
        data.character().setArmorClass(12);
        data.character().setPlatinum(0);
        data.character().setGold(0);
        data.character().setElectrum(0);
        data.character().setSilver(0);
        data.character().setCopper(0);
        when(characterClassService.getClassById(classId)).thenReturn(characterClass);
        when(characterRepository.save(any(UserCharacter.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(characterAbilityRepository.findAllByCharacterId(data.characterId())).thenReturn(List.of());
        when(characterSkillRepository.findAllByCharacterId(data.characterId())).thenReturn(List.of());
        when(characterSpellRepository.findAllByCharacterId(data.characterId())).thenReturn(List.of());
        when(characterSavingThrowRepository.findAllByCharacterId(data.characterId())).thenReturn(List.of());

        service.updateCharacterClass(data.characterId(), new SetCharacterClassRequest(classId, null));

        verify(characterSavingThrowRepository, never()).saveAll(any());
        verify(characterSkillRepository, never()).saveAll(any());
        verify(characterSpellRepository, never()).deleteAll(any());
    }


    /**
     * Возвращает данные для запрошенной операции.
     */
    @Test
    void getCharacterReturnsPersistedJoinTableFields() {
        TestData data = prepareCharacter("[]");
        Race race = race(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Эльф");
        data.character().setName("Гейл");
        data.character().setRace(race);
        data.character().setSubrace(subrace(UUID.fromString("44444444-4444-4444-4444-444444444444"), race, "Высший эльф"));
        data.character().setClassField(characterClass("Волшебник", true));
        data.character().setLevel(1);
        data.character().setMaxHealth(8);
        data.character().setCurrentHealth(8);
        data.character().setArmorClass(12);
        data.character().setPlatinum(0);
        data.character().setGold(0);
        data.character().setElectrum(0);
        data.character().setSilver(0);
        data.character().setCopper(0);
        Ability ability = ability(UUID.fromString("77777777-7777-7777-7777-777777777777"), "INT", "Интеллект");
        Skill skill = skill(UUID.fromString("88888888-8888-8888-8888-888888888888"), "Магия", "INT");
        Spell spell = spell(UUID.fromString("99999999-9999-9999-9999-999999999999"), "Волшебная стрела");
        when(characterAbilityRepository.findAllByCharacterId(data.characterId()))
                .thenReturn(List.of(characterAbility(data.character(), ability)));
        when(characterSkillRepository.findAllByCharacterId(data.characterId()))
                .thenReturn(List.of(characterSkill(data.character(), skill, 1)));
        when(characterSpellRepository.findAllByCharacterId(data.characterId()))
                .thenReturn(List.of(characterSpell(data.character(), spell)));
        when(characterSavingThrowRepository.findAllByCharacterId(data.characterId()))
                .thenReturn(List.of(characterSavingThrow(data.character(), ability, 1)));

        var response = service.getCharacter(data.characterId());

        assertEquals(List.of("Интеллект"), response.abilities());
        assertEquals(List.of("Магия"), response.skills());
        assertEquals(List.of("Волшебная стрела"), response.spells());
        assertEquals(1, response.savingThrowsCount());
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
     * @param userId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private User user(UUID userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param raceId параметр, используемый при выполнении операции
     * @param name параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Race race(UUID raceId, String name) {
        Race race = new Race();
        race.setId(raceId);
        race.setName(name);
        race.setAge(100);
        race.setHeight(170);
        race.setSpeed(30);
        return race;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param subraceId параметр, используемый при выполнении операции
     * @param race параметр, используемый при выполнении операции
     * @param name параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Subrace subrace(UUID subraceId, Race race, String name) {
        Subrace subrace = new Subrace();
        subrace.setId(subraceId);
        subrace.setRace(race);
        subrace.setName(name);
        return subrace;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @param characterClass параметр, используемый при выполнении операции
     * @param name параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private ClassArchetype classArchetype(UUID classArchetypeId, CharacterClass characterClass, String name) {
        ClassArchetype classArchetype = new ClassArchetype();
        classArchetype.setId(classArchetypeId);
        classArchetype.setCharacterClass(characterClass);
        classArchetype.setName(name);
        return classArchetype;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param abilityId параметр, используемый при выполнении операции
     * @param code параметр, используемый при выполнении операции
     * @param name параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Ability ability(UUID abilityId, String code, String name) {
        Ability ability = new Ability();
        ability.setId(abilityId);
        ability.setCode(code);
        ability.setName(name);
        return ability;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param skillId параметр, используемый при выполнении операции
     * @param name параметр, используемый при выполнении операции
     * @param abilityCode параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Skill skill(UUID skillId, String name, String abilityCode) {
        Skill skill = new Skill();
        skill.setId(skillId);
        skill.setName(name);
        skill.setAbility(abilityCode);
        return skill;
    }


    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     * @param ability параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterAbility characterAbility(UserCharacter character, Ability ability) {
        CharacterAbility characterAbility = new CharacterAbility();
        characterAbility.setCharacter(character);
        characterAbility.setAbility(ability);
        characterAbility.setValue(10);
        return characterAbility;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     * @param skill параметр, используемый при выполнении операции
     * @param proficiencyLevel параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterSkill characterSkill(UserCharacter character, Skill skill, int proficiencyLevel) {
        CharacterSkill characterSkill = new CharacterSkill();
        characterSkill.setCharacter(character);
        characterSkill.setSkill(skill);
        characterSkill.setProficiencyLevel(proficiencyLevel);
        return characterSkill;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     * @param ability параметр, используемый при выполнении операции
     * @param proficiencyLevel параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterSavingThrow characterSavingThrow(UserCharacter character, Ability ability, int proficiencyLevel) {
        CharacterSavingThrow savingThrow = new CharacterSavingThrow();
        savingThrow.setCharacter(character);
        savingThrow.setAbility(ability);
        savingThrow.setProficiencyLevel(proficiencyLevel);
        return savingThrow;
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
        return characterClass(UUID.fromString("44444444-4444-4444-4444-444444444444"), className, isSpellcaster);
    }

    /**
     * Выполняет запрошенную операцию.
     * @param classId параметр, используемый при выполнении операции
     * @param className параметр, используемый при выполнении операции
     * @param isSpellcaster параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterClass characterClass(UUID classId, String className, Boolean isSpellcaster) {
        CharacterClass characterClass = new CharacterClass();
        characterClass.setId(classId);
        characterClass.setClassName(className);
        characterClass.setIsSpellcaster(isSpellcaster);
        characterClass.setSpellcastingStartLevel(1);
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
