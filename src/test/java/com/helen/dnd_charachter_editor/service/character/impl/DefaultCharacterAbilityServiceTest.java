package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilitiesRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityValueRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceAbilityBonus;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
import com.helen.dnd_charachter_editor.entity.reference.table.SubraceAbilityBonus;
import com.helen.dnd_charachter_editor.repository.character.CharacterAbilityRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Default service implementation for default character ability service test operations.
 */
class DefaultCharacterAbilityServiceTest {

    private final AuthService authService = mock(AuthService.class);
    private final CharacterRepository characterRepository = mock(CharacterRepository.class);
    private final AbilityRepository abilityRepository = mock(AbilityRepository.class);
    private final CharacterAbilityRepository characterAbilityRepository = mock(CharacterAbilityRepository.class);
    private final RaceAbilityBonusRepository raceAbilityBonusRepository = mock(RaceAbilityBonusRepository.class);
    private final SubraceAbilityBonusRepository subraceAbilityBonusRepository = mock(SubraceAbilityBonusRepository.class);
    private final DndRulesService dndRulesService = mock(DndRulesService.class);

    private final DefaultCharacterAbilityService service = new DefaultCharacterAbilityService(
            authService,
            characterRepository,
            abilityRepository,
            characterAbilityRepository,
            raceAbilityBonusRepository,
            subraceAbilityBonusRepository,
            dndRulesService
    );

    /**
     * Sets character ability calculates final value from base and bonuses.
     */
    @Test
    void setCharacterAbilityCalculatesFinalValueFromBaseAndBonuses() {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID characterId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        Ability strength = ability(UUID.fromString("33333333-3333-3333-3333-333333333333"), "STR", "Сила");
        UserCharacter character = character(userId, characterId);

        when(authService.getCurrentUser()).thenReturn(user(userId));
        when(characterRepository.findByIdAndUser_Id(characterId, userId)).thenReturn(Optional.of(character));
        when(abilityRepository.findById(strength.getId())).thenReturn(Optional.of(strength));
        when(characterAbilityRepository.findByCharacterIdAndAbilityId(characterId, strength.getId())).thenReturn(Optional.empty());
        when(raceAbilityBonusRepository.findAllByRaceId(character.getRace().getId()))
                .thenReturn(List.of(raceBonus(character.getRace(), strength, 2)));
        when(subraceAbilityBonusRepository.findAllBySubraceId(character.getSubrace().getId()))
                .thenReturn(List.of(subraceBonus(character.getSubrace(), strength, 1)));
        when(characterAbilityRepository.save(any(CharacterAbility.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(dndRulesService.calculateAbilityModifier(18)).thenReturn(4);

        CharacterAbilityResponse response = service.setCharacterAbility(
                characterId,
                strength.getId(),
                new SetCharacterAbilityRequest(15)
        );

        assertEquals(15, response.baseValue());
        assertEquals(2, response.raceBonus());
        assertEquals(1, response.subraceBonus());
        assertEquals(18, response.totalValue());
        assertEquals(4, response.modifier());
    }

    /**
     * Sets character abilities rejects duplicate abilities.
     */
    @Test
    void setCharacterAbilitiesRejectsDuplicateAbilities() {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID characterId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID abilityId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UserCharacter character = character(userId, characterId);

        when(authService.getCurrentUser()).thenReturn(user(userId));
        when(characterRepository.findByIdAndUser_Id(characterId, userId)).thenReturn(Optional.of(character));

        SetCharacterAbilitiesRequest request = new SetCharacterAbilitiesRequest(List.of(
                new SetCharacterAbilityValueRequest(abilityId, 14),
                new SetCharacterAbilityValueRequest(abilityId, 15)
        ));

        assertThrows(IllegalArgumentException.class, () -> service.setCharacterAbilities(characterId, request));
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
        return character;
    }

    /**
     * Executes the ability operation.
     * @param abilityId value used by this operation
     * @param code value used by this operation
     * @param name value used by this operation
     * @return result of the operation
     */
    private Ability ability(UUID abilityId, String code, String name) {
        Ability ability = new Ability();
        ability.setId(abilityId);
        ability.setCode(code);
        ability.setName(name);
        return ability;
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
}
