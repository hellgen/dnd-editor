package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import com.helen.dnd_charachter_editor.entity.character.CharacterSkill;
import com.helen.dnd_charachter_editor.entity.character.CharacterSpell;
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
import com.helen.dnd_charachter_editor.repository.character.CharacterAbilityRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterSavingThrowRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterSkillRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterSpellRepository;
import com.helen.dnd_charachter_editor.repository.refernce.table.SkillRepository;
import com.helen.dnd_charachter_editor.repository.refernce.table.SpellRepository;
import com.helen.dnd_charachter_editor.service.auth.AuthService;
import com.helen.dnd_charachter_editor.service.character.CharacterAbilityService;
import com.helen.dnd_charachter_editor.service.character.CharacterService;
import com.helen.dnd_charachter_editor.service.character.CharacterWalletService;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import com.helen.dnd_charachter_editor.service.reference.table.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCharacterService implements CharacterService {

    private final AuthService authService;

    private final RaceService raceService;

    private final CharacterClassService characterClassService;

    private final CharacterAbilityService characterAbilityService;

    private final CharacterWalletService characterWalletService;

    private final CharacterRepository characterRepository;

    private final CharacterAbilityRepository characterAbilityRepository;

    private final CharacterSkillRepository characterSkillRepository;

    private final CharacterSpellRepository characterSpellRepository;

    private final CharacterSavingThrowRepository characterSavingThrowRepository;

    private final SkillRepository skillRepository;

    private final SpellRepository spellRepository;

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

        List<Skill> skills = skillRepository.findAllById(createCharacterRequest.skills());
        List<CharacterSkill> characterSkills = skills.stream()
                .map(skill -> CharacterSkillMapper.toEntity(savedCharacter, skill))
                .toList();
        characterSkillRepository.saveAll(characterSkills);

        List<Spell> spells = spellRepository.findAllById(createCharacterRequest.spells());
        List<CharacterSpell> characterSpells = spells.stream()
                .map(spell -> CharacterSpellMapper.toEntity(savedCharacter, spell))
                .toList();
        characterSpellRepository.saveAll(characterSpells);

        List<CharacterSavingThrow> characterSavingThrows = new ArrayList<>();
        for (int i = 0; i < Math.min(createCharacterRequest.savingThrowsCount(), abilities.size()); i++) {
            CharacterSavingThrow characterSavingThrow = CharacterSavingThrowMapper.toEntity(savedCharacter, abilities.get(i));
            characterSavingThrows.add(characterSavingThrow);
        }
        characterSavingThrowRepository.saveAll(characterSavingThrows);

        Map<String, Integer> wallet = createCharacterRequest.wallet() == null ? Map.of() : createCharacterRequest.wallet();

        return new CharacterResponse(
                savedCharacter.getName(),
                savedCharacter.getRace().getName(),
                savedCharacter.getSubrace() != null ? savedCharacter.getSubrace().getName() : null,
                savedCharacter.getClassField().getName(),
                savedCharacter.getClassArchetype() != null ? savedCharacter.getClassArchetype().getName() : null,
                savedCharacter.getLevel(),
                savedCharacter.getMaxHealth(),
                savedCharacter.getCurrentHealth(),
                savedCharacter.getAppearance(),
                savedCharacter.getArmorClass(),
                createCharacterRequest.inventory(),
                wallet,
                abilities.stream().map(Ability::getName).collect(Collectors.toList()),
                skills.stream().map(Skill::getName).collect(Collectors.toList()),
                spells.stream().map(Spell::getSpellName).collect(Collectors.toList()),
                characterSavingThrows.size(),
                savedCharacter.getCreatedAt(),
                savedCharacter.getUpdatedAt()
        );
    }
}
