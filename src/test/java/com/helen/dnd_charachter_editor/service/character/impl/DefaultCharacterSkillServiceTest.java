package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSkillRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSkillResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.CharacterSkill;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceAbilityBonus;
import com.helen.dnd_charachter_editor.entity.reference.table.Skill;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
import com.helen.dnd_charachter_editor.entity.reference.table.SubraceAbilityBonus;
import com.helen.dnd_charachter_editor.repository.character.CharacterAbilityRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterSkillRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.AbilityRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.RaceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.SkillRepository;
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
 * Реализация сервиса `DefaultCharacterSkillServiceTest`.
 */
class DefaultCharacterSkillServiceTest {

    private final AuthService authService = mock(AuthService.class);
    private final CharacterRepository characterRepository = mock(CharacterRepository.class);
    private final CharacterSkillRepository characterSkillRepository = mock(CharacterSkillRepository.class);
    private final CharacterAbilityRepository characterAbilityRepository = mock(CharacterAbilityRepository.class);
    private final SkillRepository skillRepository = mock(SkillRepository.class);
    private final AbilityRepository abilityRepository = mock(AbilityRepository.class);
    private final RaceAbilityBonusRepository raceAbilityBonusRepository = mock(RaceAbilityBonusRepository.class);
    private final SubraceAbilityBonusRepository subraceAbilityBonusRepository = mock(SubraceAbilityBonusRepository.class);
    private final DndRulesService dndRulesService = mock(DndRulesService.class);

    private final DefaultCharacterSkillService service = new DefaultCharacterSkillService(
            authService,
            characterRepository,
            characterSkillRepository,
            characterAbilityRepository,
            skillRepository,
            abilityRepository,
            raceAbilityBonusRepository,
            subraceAbilityBonusRepository,
            dndRulesService
    );

    /**
     * Возвращает данные для запрошенной операции.
     */
    @Test
    void getCharacterSkillsAddsProficiencyBonusWhenProficiencyIsActive() {
        TestData testData = setupSkillCalculation(1);
        when(characterSkillRepository.findAllByCharacterId(testData.character().getId()))
                .thenReturn(List.of(testData.characterSkill()));

        List<CharacterSkillResponse> responses = service.getCharacterSkills(testData.character().getId());

        assertEquals(1, responses.size());
        assertEquals(4, responses.get(0).abilityModifier());
        assertEquals(3, responses.get(0).proficiencyBonus());
        assertEquals(1, responses.get(0).proficiencyLevel());
        assertEquals(7, responses.get(0).totalModifier());
    }

    /**
     * Обновляет данные для запрошенной операции.
     */
    @Test
    void updateCharacterSkillDoublesProficiencyBonusForExpertise() {
        TestData testData = setupSkillCalculation(0);
        when(skillRepository.findById(testData.skill().getId())).thenReturn(Optional.of(testData.skill()));
        when(characterSkillRepository.findByCharacterIdAndSkillId(testData.character().getId(), testData.skill().getId()))
                .thenReturn(Optional.of(testData.characterSkill()));
        when(characterSkillRepository.save(any(CharacterSkill.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CharacterSkillResponse response = service.updateCharacterSkill(
                testData.character().getId(),
                testData.skill().getId(),
                new SetCharacterSkillRequest(2)
        );

        assertEquals(4, response.abilityModifier());
        assertEquals(3, response.proficiencyBonus());
        assertEquals(2, response.proficiencyLevel());
        assertEquals(10, response.totalModifier());
    }

    /**
     * Устанавливает данные для запрошенной операции.
     * @param proficiencyLevel параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private TestData setupSkillCalculation(Integer proficiencyLevel) {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID characterId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UserCharacter character = character(userId, characterId);
        Ability strength = ability(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        Skill athletics = skill(UUID.fromString("44444444-4444-4444-4444-444444444444"));
        CharacterAbility characterAbility = characterAbility(character, strength, 15);
        CharacterSkill characterSkill = characterSkill(character, athletics, proficiencyLevel);

        when(authService.getCurrentUser()).thenReturn(user(userId));
        when(characterRepository.findByIdAndUser_Id(characterId, userId)).thenReturn(Optional.of(character));
        when(abilityRepository.findAll()).thenReturn(List.of(strength));
        when(characterAbilityRepository.findAllByCharacterId(characterId)).thenReturn(List.of(characterAbility));
        when(raceAbilityBonusRepository.findAllByRaceId(character.getRace().getId()))
                .thenReturn(List.of(raceBonus(character.getRace(), strength, 2)));
        when(subraceAbilityBonusRepository.findAllBySubraceId(character.getSubrace().getId()))
                .thenReturn(List.of(subraceBonus(character.getSubrace(), strength, 1)));
        when(dndRulesService.calculateAbilityModifier(18)).thenReturn(4);
        when(dndRulesService.calculateProficiencyBonus(5)).thenReturn(3);

        return new TestData(character, strength, athletics, characterSkill);
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
     * @param userId параметр, используемый при выполнении операции
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private UserCharacter character(UUID userId, UUID characterId) {
        Race race = new Race();
        race.setId(UUID.fromString("55555555-5555-5555-5555-555555555555"));

        Subrace subrace = new Subrace();
        subrace.setId(UUID.fromString("66666666-6666-6666-6666-666666666666"));
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
     * Выполняет запрошенную операцию.
     * @param abilityId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Ability ability(UUID abilityId) {
        Ability ability = new Ability();
        ability.setId(abilityId);
        ability.setCode("STRENGTH");
        ability.setName("Сила");
        return ability;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param skillId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Skill skill(UUID skillId) {
        Skill skill = new Skill();
        skill.setId(skillId);
        skill.setName("Атлетика");
        skill.setAbility("STR");
        return skill;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     * @param ability параметр, используемый при выполнении операции
     * @param value параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterAbility characterAbility(UserCharacter character, Ability ability, Integer value) {
        CharacterAbility characterAbility = new CharacterAbility();
        characterAbility.setCharacter(character);
        characterAbility.setAbility(ability);
        characterAbility.setValue(value);
        return characterAbility;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     * @param skill параметр, используемый при выполнении операции
     * @param proficiencyLevel параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterSkill characterSkill(UserCharacter character, Skill skill, Integer proficiencyLevel) {
        CharacterSkill characterSkill = new CharacterSkill();
        characterSkill.setCharacter(character);
        characterSkill.setSkill(skill);
        characterSkill.setProficiencyLevel(proficiencyLevel);
        return characterSkill;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param race параметр, используемый при выполнении операции
     * @param ability параметр, используемый при выполнении операции
     * @param bonusValue параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private RaceAbilityBonus raceBonus(Race race, Ability ability, Integer bonusValue) {
        RaceAbilityBonus raceAbilityBonus = new RaceAbilityBonus();
        raceAbilityBonus.setRace(race);
        raceAbilityBonus.setAbility(ability);
        raceAbilityBonus.setBonusValue(bonusValue);
        return raceAbilityBonus;
    }

    /**
     * Выполняет запрошенную операцию.
     * @param subrace параметр, используемый при выполнении операции
     * @param ability параметр, используемый при выполнении операции
     * @param bonusValue параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private SubraceAbilityBonus subraceBonus(Subrace subrace, Ability ability, Integer bonusValue) {
        SubraceAbilityBonus subraceAbilityBonus = new SubraceAbilityBonus();
        subraceAbilityBonus.setSubrace(subrace);
        subraceAbilityBonus.setAbility(ability);
        subraceAbilityBonus.setBonusValue(bonusValue);
        return subraceAbilityBonus;
    }

    /**
     * Реализация сервиса `TestData`.
     */
    private record TestData(
            UserCharacter character,
            Ability ability,
            Skill skill,
            CharacterSkill characterSkill
    ) {
    }
}
