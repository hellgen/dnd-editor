package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterRaceRequest;
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
import java.util.UUID;
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
    @Transactional(readOnly = true)
    public CharacterResponse getCharacter(UUID characterId) {
        User user = authService.getCurrentUser();
        UserCharacter character = characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        return buildCharacterResponse(character);
    }

    @Override
    @Transactional
    public CharacterResponse updateCharacter(UUID characterId, CreateCharacterRequest createCharacterRequest) {
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
        int proficientSavingThrowsCount = Math.min(createCharacterRequest.savingThrowsCount(), 2);
        int currentProficientSavingThrowsCount = (int) currentSavingThrows.stream()
                .filter(savingThrow -> savingThrow.getProficiencyLevel() > 0)
                .count();
        if (!currentSavingThrows.stream().map(st -> st.getAbility().getId()).collect(Collectors.toSet()).equals(savingThrowAbilityIds)
                || currentProficientSavingThrowsCount != proficientSavingThrowsCount) {
            characterSavingThrowRepository.deleteAll(currentSavingThrows);
            List<CharacterSavingThrow> savingThrows = new ArrayList<>();
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

    @Override
    @Transactional
    public CharacterResponse updateCharacterLevel(UUID characterId, Integer level) {
        if (level == null || level <= 0) {
            throw new IllegalArgumentException("Level must be greater than 0");
        }

        User user = authService.getCurrentUser();
        UserCharacter character = characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        character.setLevel(level);
        UserCharacter savedCharacter = characterRepository.save(character);

        return buildCharacterResponse(savedCharacter);
    }

    @Override
    @Transactional
    public CharacterResponse updateCharacterHealth(UUID characterId, Integer maxHealth, Integer currentHealth) {
        validateHealth(maxHealth, currentHealth);

        User user = authService.getCurrentUser();
        UserCharacter character = characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        character.setMaxHealth(maxHealth);
        character.setCurrentHealth(currentHealth);
        UserCharacter savedCharacter = characterRepository.save(character);

        return buildCharacterResponse(savedCharacter);
    }

    @Override
    @Transactional
    public CharacterResponse applyCharacterClass(UUID characterId, SetCharacterClassRequest request) {
        return setCharacterClass(characterId, request);
    }

    @Override
    @Transactional
    public CharacterResponse updateCharacterClass(UUID characterId, SetCharacterClassRequest request) {
        return setCharacterClass(characterId, request);
    }

    @Override
    @Transactional
    public CharacterResponse applyCharacterRace(UUID characterId, SetCharacterRaceRequest request) {
        return setCharacterRace(characterId, request);
    }

    @Override
    @Transactional
    public CharacterResponse updateCharacterRace(UUID characterId, SetCharacterRaceRequest request) {
        return setCharacterRace(characterId, request);
    }

    @Override
    @Transactional
    public void deleteCharacter(UUID characterId) {
        User user = authService.getCurrentUser();
        UserCharacter character = characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        characterRepository.delete(character);
    }

    private CharacterResponse setCharacterClass(UUID characterId, SetCharacterClassRequest request) {
        if (request.classId() == null) {
            throw new IllegalArgumentException("classId is required");
        }

        User user = authService.getCurrentUser();
        UserCharacter character = characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        CharacterClass characterClass = characterClassService.getClassById(request.classId());
        ClassArchetype classArchetype = getClassArchetypeOrNull(request.classId(), request.classArchetypeId());

        character.setClassField(characterClass);
        character.setClassArchetype(classArchetype);

        UserCharacter savedCharacter = characterRepository.save(character);

        return buildCharacterResponse(savedCharacter);
    }

    private ClassArchetype getClassArchetypeOrNull(UUID classId, UUID classArchetypeId) {
        if (classArchetypeId == null) {
            return null;
        }

        return characterClassService.getClassArchetypeById(classId, classArchetypeId);
    }

    private CharacterResponse setCharacterRace(UUID characterId, SetCharacterRaceRequest request) {
        if (request.raceId() == null) {
            throw new IllegalArgumentException("raceId is required");
        }

        User user = authService.getCurrentUser();
        UserCharacter character = characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        Race race = raceService.getRace(request.raceId());
        Subrace subrace = getSubraceOrNull(request.raceId(), request.subraceId());

        character.setRace(race);
        character.setSubrace(subrace);

        UserCharacter savedCharacter = characterRepository.save(character);

        return buildCharacterResponse(savedCharacter);
    }

    private Subrace getSubraceOrNull(UUID raceId, UUID subraceId) {
        if (subraceId == null) {
            return null;
        }

        return raceService.getSubrace(raceId, subraceId);
    }

    private void validateHealth(Integer maxHealth, Integer currentHealth) {
        if (maxHealth == null || maxHealth <= 0) {
            throw new IllegalArgumentException("maxHealth must be greater than 0");
        }

        if (currentHealth == null) {
            throw new IllegalArgumentException("currentHealth is required");
        }

        if (currentHealth > maxHealth) {
            throw new IllegalArgumentException("currentHealth must be less than or equal to maxHealth");
        }

        if (currentHealth < -(maxHealth / 2)) {
            throw new IllegalArgumentException("currentHealth must be greater than or equal to negative half of maxHealth");
        }
    }

    private CharacterResponse buildCharacterResponse(UserCharacter character) {
        List<UUID> abilityIds = CharacterResponseMapper.deserializeIds(character.getAbilities());
        List<UUID> spellIds = CharacterResponseMapper.deserializeIds(character.getSpells());

        return CharacterResponseMapper.toResponse(
                character,
                abilityRepository.findAllById(abilityIds),
                characterSkillRepository.findAllByCharacterId(character.getId()),
                spellRepository.findAllById(spellIds),
                character.getSavingThrowsCount()
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
        character.setInventory(CharacterResponseMapper.serializeInventory(request.inventory()));
        character.setAbilities(CharacterResponseMapper.serializeIds(request.abilities()));
        character.setSpells(CharacterResponseMapper.serializeIds(request.spells()));
        character.setSavingThrowsCount(Math.min(request.savingThrowsCount(), 2));
    }
}
