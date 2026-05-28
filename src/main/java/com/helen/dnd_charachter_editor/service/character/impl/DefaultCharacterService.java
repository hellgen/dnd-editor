package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
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
import com.helen.dnd_charachter_editor.mapper.character.CharacterMapper;
import com.helen.dnd_charachter_editor.mapper.character.CharacterAbilityMapper;
import com.helen.dnd_charachter_editor.mapper.character.CharacterSavingThrowMapper;
import com.helen.dnd_charachter_editor.mapper.character.CharacterSkillMapper;
import com.helen.dnd_charachter_editor.mapper.character.CharacterSpellMapper;
import com.helen.dnd_charachter_editor.mapper.character.CharacterResponseMapper;
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
import com.helen.dnd_charachter_editor.service.character.CharacterService;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import com.helen.dnd_charachter_editor.service.reference.table.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class DefaultCharacterService implements CharacterService {

    private final AuthService authService;

    private final RaceService raceService;

    private final CharacterClassService characterClassService;

    private final CharacterAbilityService characterAbilityService;

    private final CharacterRepository characterRepository;

    private final CharacterAbilityRepository characterAbilityRepository;

    private final CharacterSkillRepository characterSkillRepository;

    private final CharacterSpellRepository characterSpellRepository;

    private final CharacterSavingThrowRepository characterSavingThrowRepository;

    private final SkillRepository skillRepository;

    private final SpellRepository spellRepository;

    private final AbilityRepository abilityRepository;

    @Override
    @Transactional
    public CharacterResponse createCharacter(CreateCharacterRequest createCharacterRequest) {

        User user = authService.getCurrentUser();

        Race race = raceService.getRace(createCharacterRequest.raceId());

        Subrace subrace = raceService.getSubrace(createCharacterRequest.raceId(), createCharacterRequest.subraceId());

        CharacterClass characterClass = characterClassService.getClassById(createCharacterRequest.classID());

        ClassArchetype classArchetype = characterClassService.getClassArchetypeById(
                createCharacterRequest.classID(),
                createCharacterRequest.classArchetypeId()
        );

        UserCharacter userCharacter = CharacterMapper.mapToUserCharacter(
                createCharacterRequest,
                user,
                race,
                subrace,
                characterClass,
                classArchetype
        );

        UserCharacter savedCharacter = characterRepository.save(userCharacter);

        List<Ability> abilities = characterAbilityRepository.findAllByIds(createCharacterRequest.abilities());
        List<CharacterAbility> characterAbilities = abilities.stream()
                .map(ability -> CharacterAbilityMapper.toEntity(savedCharacter, ability))
                .toList();
        characterAbilityRepository.saveAll(characterAbilities);

        List<Skill> allSkills = skillRepository.findAll();
        Set<java.util.UUID> selectedSkillIds = new HashSet<>(createCharacterRequest.skills());
        List<CharacterSkill> characterSkills = allSkills.stream().map(skill -> {
            CharacterSkill entity = CharacterSkillMapper.toEntity(savedCharacter, skill);
            if (selectedSkillIds.contains(skill.getId())) {
                entity.setProficiencyLevel(1);
            }
            return entity;
        }).toList();
        characterSkillRepository.saveAll(characterSkills);

        List<Spell> spells = spellRepository.findAllById(createCharacterRequest.spells());
        List<CharacterSpell> characterSpells = spells.stream()
                .map(spell -> CharacterSpellMapper.toEntity(savedCharacter, spell))
                .toList();
        characterSpellRepository.saveAll(characterSpells);

        List<Ability> allAbilities = abilityRepository.findAll();
        List<CharacterSavingThrow> characterSavingThrows = new ArrayList<>();
        int proficientSavingThrowsCount = Math.min(createCharacterRequest.savingThrowsCount(), 2);
        for (int i = 0; i < allAbilities.size(); i++) {
            CharacterSavingThrow characterSavingThrow = CharacterSavingThrowMapper.toEntity(savedCharacter, allAbilities.get(i));
            if (i < proficientSavingThrowsCount) {
                characterSavingThrow.setProficiencyLevel(1);
            }
            characterSavingThrows.add(characterSavingThrow);
        }
        characterSavingThrowRepository.saveAll(characterSavingThrows);

        return CharacterResponseMapper.toResponse(savedCharacter, createCharacterRequest, abilities, allSkills, spells, proficientSavingThrowsCount);
    }

    @Override
    @Transactional
    public CharacterResponse updateCharacter(java.util.UUID characterId, CreateCharacterRequest createCharacterRequest) {
        UserCharacter character = characterRepository.findById(characterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        Race race = raceService.getRace(createCharacterRequest.raceId());
        Subrace subrace = raceService.getSubrace(createCharacterRequest.raceId(), createCharacterRequest.subraceId());
        CharacterClass characterClass = characterClassService.getClassById(createCharacterRequest.classID());
        ClassArchetype classArchetype = characterClassService.getClassArchetypeById(
                createCharacterRequest.classID(),
                createCharacterRequest.classArchetypeId()
        );

        applyMainCharacterFields(character, createCharacterRequest, race, subrace, characterClass, classArchetype);

        UserCharacter savedCharacter = characterRepository.save(character);

        List<Ability> abilities = characterAbilityRepository.findAllByIds(createCharacterRequest.abilities());
        Set<java.util.UUID> abilityIds = abilities.stream().map(Ability::getId).collect(Collectors.toSet());
        List<CharacterAbility> currentAbilities = characterAbilityRepository.findAllByCharacterId(characterId);
        if (!currentAbilities.stream().map(a -> a.getAbility().getId()).collect(Collectors.toSet()).equals(abilityIds)) {
            characterAbilityRepository.deleteAll(currentAbilities);
            characterAbilityRepository.saveAll(abilities.stream().map(a -> CharacterAbilityMapper.toEntity(savedCharacter, a)).toList());
        }

        List<Skill> skills = skillRepository.findAll();
        Set<java.util.UUID> skillIds = new HashSet<>(createCharacterRequest.skills());
        List<CharacterSkill> currentSkills = characterSkillRepository.findAllByCharacterId(characterId);
        if (!currentSkills.stream().map(s -> s.getSkill().getId()).collect(Collectors.toSet()).equals(skillIds)) {
            characterSkillRepository.deleteAll(currentSkills);
            List<CharacterSkill> updatedSkills = skills.stream().map(skill -> {
                CharacterSkill entity = CharacterSkillMapper.toEntity(savedCharacter, skill);
                if (skillIds.contains(skill.getId())) {
                    entity.setProficiencyLevel(1);
                }
                return entity;
            }).toList();
            characterSkillRepository.saveAll(updatedSkills);
        }

        List<Spell> spells = spellRepository.findAllById(createCharacterRequest.spells());
        Set<java.util.UUID> spellIds = spells.stream().map(Spell::getId).collect(Collectors.toSet());
        List<CharacterSpell> currentSpells = characterSpellRepository.findAllByCharacterId(characterId);
        if (!currentSpells.stream().map(s -> s.getSpell().getId()).collect(Collectors.toSet()).equals(spellIds)) {
            characterSpellRepository.deleteAll(currentSpells);
            characterSpellRepository.saveAll(spells.stream().map(s -> CharacterSpellMapper.toEntity(savedCharacter, s)).toList());
        }

        List<Ability> allAbilities = abilityRepository.findAll();
        Set<java.util.UUID> savingThrowAbilityIds = allAbilities.stream().map(Ability::getId).collect(Collectors.toSet());
        List<CharacterSavingThrow> currentSavingThrows = characterSavingThrowRepository.findAllByCharacterId(characterId);
        if (!currentSavingThrows.stream().map(st -> st.getAbility().getId()).collect(Collectors.toSet()).equals(savingThrowAbilityIds)) {
            characterSavingThrowRepository.deleteAll(currentSavingThrows);
            List<CharacterSavingThrow> savingThrows = new ArrayList<>();
            int proficientSavingThrowsCount = Math.min(createCharacterRequest.savingThrowsCount(), 2);
            for (int i = 0; i < allAbilities.size(); i++) {
                CharacterSavingThrow entity = CharacterSavingThrowMapper.toEntity(savedCharacter, allAbilities.get(i));
                if (i < proficientSavingThrowsCount) {
                    entity.setProficiencyLevel(1);
                }
                savingThrows.add(entity);
            }
            characterSavingThrowRepository.saveAll(savingThrows);
        }

        return CharacterResponseMapper.toResponse(
                savedCharacter,
                createCharacterRequest,
                abilities,
                skills,
                spells,
                Math.min(createCharacterRequest.savingThrowsCount(), 2)
        );
    }

    private void applyMainCharacterFields(
            UserCharacter character,
            CreateCharacterRequest request,
            Race race,
            Subrace subrace,
            CharacterClass characterClass,
            ClassArchetype classArchetype
    ) {
        character.setName(request.characterName());
        character.setRace(race);
        character.setSubrace(subrace);
        character.setClassField(characterClass);
        character.setClassArchetype(classArchetype);
        character.setLevel(request.level());
        character.setMaxHealth(request.maxHealth());
        character.setCurrentHealth(request.currentHealth());
        character.setAppearance(request.appearance());
        character.setArmorClass(request.armorClass());
    }
}
