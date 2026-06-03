package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSavingThrowRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSavingThrowResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceAbilityBonus;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
import com.helen.dnd_charachter_editor.entity.reference.table.SubraceAbilityBonus;
import com.helen.dnd_charachter_editor.repository.character.CharacterAbilityRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterSavingThrowRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.AbilityRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.RaceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.SubraceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.service.auth.AuthService;
import com.helen.dnd_charachter_editor.service.character.DndRulesService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Default service implementation for default character saving throw service test operations.
 */
class DefaultCharacterSavingThrowServiceTest {

    private final AuthService authService = mock(AuthService.class);
    private final CharacterRepository characterRepository = mock(CharacterRepository.class);
    private final CharacterSavingThrowRepository characterSavingThrowRepository = mock(CharacterSavingThrowRepository.class);
    private final CharacterAbilityRepository characterAbilityRepository = mock(CharacterAbilityRepository.class);
    private final AbilityRepository abilityRepository = mock(AbilityRepository.class);
    private final RaceAbilityBonusRepository raceAbilityBonusRepository = mock(RaceAbilityBonusRepository.class);
    private final SubraceAbilityBonusRepository subraceAbilityBonusRepository = mock(SubraceAbilityBonusRepository.class);
    private final DndRulesService dndRulesService = mock(DndRulesService.class);

    private final DefaultCharacterSavingThrowService service = new DefaultCharacterSavingThrowService(
            authService,
            characterRepository,
            characterSavingThrowRepository,
            characterAbilityRepository,
            abilityRepository,
            raceAbilityBonusRepository,
            subraceAbilityBonusRepository,
            dndRulesService
    );

    /**
     * Returns character saving throws adds proficiency bonus when proficient.
     */
    @Test
    void getCharacterSavingThrowsAddsProficiencyBonusWhenProficient() {
        TestData testData = setupSavingThrowCalculation(1);
        when(characterSavingThrowRepository.findAllByCharacterId(testData.character().getId()))
                .thenReturn(List.of(testData.savingThrow()));

        List<CharacterSavingThrowResponse> responses = service.getCharacterSavingThrows(testData.character().getId());

        assertEquals(1, responses.size());
        assertEquals(4, responses.get(0).abilityModifier());
        assertEquals(3, responses.get(0).proficiencyBonus());
        assertEquals(1, responses.get(0).proficiencyLevel());
        assertEquals(7, responses.get(0).totalModifier());
    }

    /**
     * Updates character saving throw recalculates modifier from current ability and bonuses.
     */
    @Test
    void updateCharacterSavingThrowRecalculatesModifierFromCurrentAbilityAndBonuses() {
        TestData testData = setupSavingThrowCalculation(0);
        when(abilityRepository.findById(testData.ability().getId())).thenReturn(Optional.of(testData.ability()));
        when(characterSavingThrowRepository.findByCharacterIdAndAbilityId(testData.character().getId(), testData.ability().getId()))
                .thenReturn(Optional.of(testData.savingThrow()));
        when(characterSavingThrowRepository.save(any(CharacterSavingThrow.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CharacterSavingThrowResponse response = service.updateCharacterSavingThrow(
                testData.character().getId(),
                testData.ability().getId(),
                new SetCharacterSavingThrowRequest(1)
        );

        assertEquals(4, response.abilityModifier());
        assertEquals(3, response.proficiencyBonus());
        assertEquals(1, response.proficiencyLevel());
        assertEquals(7, response.totalModifier());
    }

    /**
     * Sets up saving throw calculation.
     * @param proficiencyLevel value used by this operation
     * @return result of the operation
     */
    private TestData setupSavingThrowCalculation(Integer proficiencyLevel) {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID characterId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UserCharacter character = character(userId, characterId);
        Ability strength = ability(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        CharacterAbility characterAbility = characterAbility(character, strength, 15);
        CharacterSavingThrow savingThrow = savingThrow(character, strength, proficiencyLevel);

        when(authService.getCurrentUser()).thenReturn(user(userId));
        when(characterRepository.findByIdAndUser_Id(characterId, userId)).thenReturn(Optional.of(character));
        when(characterAbilityRepository.findAllByCharacterId(characterId)).thenReturn(List.of(characterAbility));
        when(raceAbilityBonusRepository.findAllByRaceId(character.getRace().getId()))
                .thenReturn(List.of(raceBonus(character.getRace(), strength, 2)));
        when(subraceAbilityBonusRepository.findAllBySubraceId(character.getSubrace().getId()))
                .thenReturn(List.of(subraceBonus(character.getSubrace(), strength, 1)));
        when(dndRulesService.calculateAbilityModifier(18)).thenReturn(4);
        when(dndRulesService.calculateProficiencyBonus(5)).thenReturn(3);

        return new TestData(character, strength, savingThrow);
    }

    /**
     * Executes the user operation.
     * @param userId value used by this operation
     * @return result of the operation
     */
    private User user(UUID userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    /**
     * Executes the character operation.
     * @param userId value used by this operation
     * @param characterId value used by this operation
     * @return result of the operation
     */
    private UserCharacter character(UUID userId, UUID characterId) {
        Race race = new Race();
        race.setId(UUID.fromString("44444444-4444-4444-4444-444444444444"));

        Subrace subrace = new Subrace();
        subrace.setId(UUID.fromString("55555555-5555-5555-5555-555555555555"));
        subrace.setRace(race);

        UserCharacter character = new UserCharacter();
        character.setId(characterId);
        character.setUser(user(userId));
        character.setRace(race);
        character.setSubrace(subrace);
        character.setLevel(5);
        return character;
    }

    /**
     * Executes the ability operation.
     * @param abilityId value used by this operation
     * @return result of the operation
     */
    private Ability ability(UUID abilityId) {
        Ability ability = new Ability();
        ability.setId(abilityId);
        ability.setCode("STRENGTH");
        ability.setName("Сила");
        return ability;
    }

    /**
     * Executes the character ability operation.
     * @param character value used by this operation
     * @param ability value used by this operation
     * @param value value used by this operation
     * @return result of the operation
     */
    private CharacterAbility characterAbility(UserCharacter character, Ability ability, Integer value) {
        CharacterAbility characterAbility = new CharacterAbility();
        characterAbility.setCharacter(character);
        characterAbility.setAbility(ability);
        characterAbility.setValue(value);
        return characterAbility;
    }

    /**
     * Executes the saving throw operation.
     * @param character value used by this operation
     * @param ability value used by this operation
     * @param proficiencyLevel value used by this operation
     * @return result of the operation
     */
    private CharacterSavingThrow savingThrow(UserCharacter character, Ability ability, Integer proficiencyLevel) {
        CharacterSavingThrow savingThrow = new CharacterSavingThrow();
        savingThrow.setCharacter(character);
        savingThrow.setAbility(ability);
        savingThrow.setProficiencyLevel(proficiencyLevel);
        return savingThrow;
    }

    /**
     * Executes the race bonus operation.
     * @param race value used by this operation
     * @param ability value used by this operation
     * @param bonusValue value used by this operation
     * @return result of the operation
     */
    private RaceAbilityBonus raceBonus(Race race, Ability ability, Integer bonusValue) {
        RaceAbilityBonus raceAbilityBonus = new RaceAbilityBonus();
        raceAbilityBonus.setRace(race);
        raceAbilityBonus.setAbility(ability);
        raceAbilityBonus.setBonusValue(bonusValue);
        return raceAbilityBonus;
    }

    /**
     * Executes the subrace bonus operation.
     * @param subrace value used by this operation
     * @param ability value used by this operation
     * @param bonusValue value used by this operation
     * @return result of the operation
     */
    private SubraceAbilityBonus subraceBonus(Subrace subrace, Ability ability, Integer bonusValue) {
        SubraceAbilityBonus subraceAbilityBonus = new SubraceAbilityBonus();
        subraceAbilityBonus.setSubrace(subrace);
        subraceAbilityBonus.setAbility(ability);
        subraceAbilityBonus.setBonusValue(bonusValue);
        return subraceAbilityBonus;
    }

    /**
     * Default service implementation for test data operations.
     */
    private record TestData(
            UserCharacter character,
            Ability ability,
            CharacterSavingThrow savingThrow
    ) {
    }
}
