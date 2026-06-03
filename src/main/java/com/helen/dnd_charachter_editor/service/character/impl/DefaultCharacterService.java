package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterSpellRequest;
import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassArchetypeRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterRaceRequest;
import com.helen.dnd_charachter_editor.dto.request.character.UpdateCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.WalletUpdateRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterInventoryResponse;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.dto.response.character.WalletResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
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
import com.helen.dnd_charachter_editor.mapper.reference.table.SpellMapper;
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
import com.helen.dnd_charachter_editor.service.reference.table.SpellService;
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

/**
 * Реализация сервиса `DefaultCharacterService`.
 */
@Service
@RequiredArgsConstructor
public class DefaultCharacterService implements CharacterService {

    private final AuthService authService;

    private final RaceService raceService;

    private final CharacterClassService characterClassService;

    private final CharacterAbilityService characterAbilityService;

    private final SpellService spellService;

    private final CharacterRepository characterRepository;

    private final CharacterAbilityRepository characterAbilityRepository;

    private final CharacterSkillRepository characterSkillRepository;

    private final CharacterSpellRepository characterSpellRepository;

    private final CharacterSavingThrowRepository characterSavingThrowRepository;

    private final SkillRepository skillRepository;

    private final SpellRepository spellRepository;

    private final AbilityRepository abilityRepository;

    /**
     * Создаёт данные для запрошенной операции.
     * @param createCharacterRequest параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
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

        UserCharacter savedCharacter = characterRepository.saveAndFlush(userCharacter);

        List<UUID> requestedAbilityIds = requestIds(createCharacterRequest.abilities());
        List<Ability> abilities = requestedAbilityIds.isEmpty()
                ? List.of()
                : characterAbilityRepository.findAllByIds(requestedAbilityIds);
        validateReferenceIds(requestedAbilityIds, abilities.stream().map(Ability::getId).collect(Collectors.toSet()), "Abilities not found");
        List<CharacterAbility> characterAbilities = characterAbilityRepository.saveAllAndFlush(abilities.stream()
                .map(ability -> CharacterAbilityMapper.toEntity(savedCharacter, ability))
                .toList());

        List<Skill> allSkills = skillRepository.findAll();
        Set<UUID> selectedSkillIds = new HashSet<>(requestIds(createCharacterRequest.skills()));
        validateReferenceIds(
                selectedSkillIds.stream().toList(),
                allSkills.stream().map(Skill::getId).collect(Collectors.toSet()),
                "Skills not found"
        );
        List<CharacterSkill> characterSkills = characterSkillRepository.saveAllAndFlush(allSkills.stream().map(skill -> {
            CharacterSkill entity = CharacterSkillMapper.toEntity(savedCharacter, skill);
            if (selectedSkillIds.contains(skill.getId())) {
                entity.setProficiencyLevel(1);
            }
            return entity;
        }).toList());

        List<UUID> requestedSpellIds = requestIds(createCharacterRequest.spells());
        List<Spell> spells = spellRepository.findAllById(requestedSpellIds);
        validateReferenceIds(requestedSpellIds, spells.stream().map(Spell::getId).collect(Collectors.toSet()), "Spells not found");
        List<CharacterSpell> characterSpells = characterSpellRepository.saveAllAndFlush(spells.stream()
                .map(spell -> CharacterSpellMapper.toEntity(savedCharacter, spell))
                .toList());

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
        characterSavingThrows = characterSavingThrowRepository.saveAllAndFlush(characterSavingThrows);

        return CharacterResponseMapper.toResponse(
                savedCharacter,
                characterAbilities,
                characterSkills,
                characterSpells,
                characterSavingThrows
        );
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional(readOnly = true)
    public CharacterResponse getCharacter(UUID characterId) {
        User user = authService.getCurrentUser();
        UserCharacter character = characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        return buildCharacterResponse(character);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param createCharacterRequest параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
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

        List<UUID> requestedAbilityIds = requestIds(createCharacterRequest.abilities());
        List<Ability> abilities = requestedAbilityIds.isEmpty()
                ? List.of()
                : characterAbilityRepository.findAllByIds(requestedAbilityIds);
        validateReferenceIds(requestedAbilityIds, abilities.stream().map(Ability::getId).collect(Collectors.toSet()), "Abilities not found");
        Set<UUID> abilityIds = abilities.stream().map(Ability::getId).collect(Collectors.toSet());
        List<CharacterAbility> currentAbilities = characterAbilityRepository.findAllByCharacterId(characterId);
        if (!currentAbilities.stream().map(a -> a.getAbility().getId()).collect(Collectors.toSet()).equals(abilityIds)) {
            characterAbilityRepository.deleteAll(currentAbilities);
            characterAbilityRepository.saveAllAndFlush(abilities.stream().map(a -> CharacterAbilityMapper.toEntity(savedCharacter, a)).toList());
        }

        List<Skill> skills = skillRepository.findAll();
        Set<UUID> skillIds = new HashSet<>(requestIds(createCharacterRequest.skills()));
        validateReferenceIds(
                skillIds.stream().toList(),
                skills.stream().map(Skill::getId).collect(Collectors.toSet()),
                "Skills not found"
        );
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
            characterSkillRepository.saveAllAndFlush(updatedSkills);
        }

        List<UUID> requestedSpellIds = requestIds(createCharacterRequest.spells());
        List<Spell> spells = spellRepository.findAllById(requestedSpellIds);
        validateReferenceIds(requestedSpellIds, spells.stream().map(Spell::getId).collect(Collectors.toSet()), "Spells not found");
        Set<UUID> spellIds = spells.stream().map(Spell::getId).collect(Collectors.toSet());
        List<CharacterSpell> currentSpells = characterSpellRepository.findAllByCharacterId(characterId);
        if (!currentSpells.stream().map(s -> s.getSpell().getId()).collect(Collectors.toSet()).equals(spellIds)) {
            characterSpellRepository.deleteAll(currentSpells);
            characterSpellRepository.saveAllAndFlush(spells.stream().map(s -> CharacterSpellMapper.toEntity(savedCharacter, s)).toList());
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
            characterSavingThrowRepository.saveAllAndFlush(savingThrows);
        }

        return buildCharacterResponse(savedCharacter);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param level параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
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

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param maxHealth параметр, используемый при выполнении операции
     * @param currentHealth параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
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

    /**
     * Применяет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public CharacterResponse applyCharacterClass(UUID characterId, SetCharacterClassRequest request) {
        return setCharacterClass(characterId, request);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public CharacterResponse updateCharacterClass(UUID characterId, SetCharacterClassRequest request) {
        return setCharacterClass(characterId, request);
    }

    /**
     * Применяет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public CharacterResponse applyCharacterClassArchetype(
            UUID characterId,
            SetCharacterClassArchetypeRequest request
    ) {
        return setCharacterClassArchetype(characterId, request);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public CharacterResponse updateCharacterClassArchetype(
            UUID characterId,
            SetCharacterClassArchetypeRequest request
    ) {
        return setCharacterClassArchetype(characterId, request);
    }

    /**
     * Применяет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public CharacterResponse applyCharacterRace(UUID characterId, SetCharacterRaceRequest request) {
        return setCharacterRace(characterId, request);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public CharacterResponse updateCharacterRace(UUID characterId, SetCharacterRaceRequest request) {
        return setCharacterRace(characterId, request);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpellResponse> getCharacterSpells(UUID characterId) {
        UserCharacter character = findCharacterForCurrentUser(characterId);
        return characterSpellRepository.findAllByCharacterId(character.getId()).stream()
                .map(CharacterSpell::getSpell)
                .map(SpellMapper::toSpellResponse)
                .toList();
    }

    /**
     * Добавляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public SpellResponse addCharacterSpell(UUID characterId, AddCharacterSpellRequest request) {
        if (request == null || request.spellId() == null) {
            throw new IllegalArgumentException("spellId is required");
        }

        UserCharacter character = findCharacterForCurrentUser(characterId);
        Spell spell = spellService.getSpell(request.spellId());

        if (!spellService.isSpellAvailableForClass(character.getClassField(), spell)) {
            throw new IllegalArgumentException("Spell is not available for character class");
        }

        if (characterSpellRepository.existsByCharacterIdAndSpellId(character.getId(), spell.getId())) {
            throw new IllegalArgumentException("Spell already added to character");
        }

        CharacterSpell characterSpell = CharacterSpellMapper.toEntity(character, spell);
        characterSpellRepository.save(characterSpell);
        character.setSpells(CharacterResponseMapper.serializeIds(addSpellId(character.getSpells(), spell.getId())));
        characterRepository.save(character);

        return SpellMapper.toSpellResponse(spell);
    }

    /**
     * Удаляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param spellId параметр, используемый при выполнении операции
     */
    @Override
    @Transactional
    public void deleteCharacterSpell(UUID characterId, UUID spellId) {
        UserCharacter character = findCharacterForCurrentUser(characterId);
        CharacterSpell characterSpell = characterSpellRepository.findByCharacterIdAndSpellId(character.getId(), spellId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character spell not found"));

        characterSpellRepository.delete(characterSpell);
        character.setSpells(CharacterResponseMapper.serializeIds(removeSpellId(character.getSpells(), spellId)));
        characterRepository.save(character);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional(readOnly = true)
    public List<CharacterInventoryResponse> getCharacterInventory(UUID characterId) {
        UserCharacter character = findCharacterForCurrentUser(characterId);
        return readInventory(character);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param itemName параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional(readOnly = true)
    public CharacterInventoryResponse getCharacterInventoryItem(UUID characterId, String itemName) {
        UserCharacter character = findCharacterForCurrentUser(characterId);
        return findInventoryItem(readInventory(character), itemName);
    }

    /**
     * Добавляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public CharacterInventoryResponse addCharacterInventoryItem(UUID characterId, AddCharacterInventoryRequest request) {
        validateAddInventoryRequest(request);
        UserCharacter character = findCharacterForCurrentUser(characterId);
        List<CharacterInventoryResponse> inventory = new ArrayList<>(readInventory(character));

        CharacterInventoryResponse savedItem = findInventoryItemOrNull(inventory, request.itemName());
        if (savedItem == null) {
            savedItem = new CharacterInventoryResponse(
                    UUID.randomUUID(),
                    character.getId(),
                    request.itemId(),
                    normalizedItemName(request.itemName()),
                    request.itemDescription(),
                    request.quantity(),
                    Boolean.TRUE.equals(request.isEquipped()),
                    request.customDescription()
            );
            inventory.add(savedItem);
        } else {
            savedItem = new CharacterInventoryResponse(
                    savedItem.id() != null ? savedItem.id() : UUID.randomUUID(),
                    character.getId(),
                    request.itemId() != null ? request.itemId() : savedItem.itemId(),
                    savedItem.itemName(),
                    request.itemDescription() != null ? request.itemDescription() : savedItem.itemDescription(),
                    savedItem.quantity() + request.quantity(),
                    request.isEquipped() != null ? request.isEquipped() : savedItem.isEquipped(),
                    request.customDescription() != null ? request.customDescription() : savedItem.customDescription()
            );
            replaceInventoryItem(inventory, savedItem);
        }

        saveInventory(character, inventory);
        return savedItem;
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param requests параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public List<CharacterInventoryResponse> updateCharacterInventoryItems(
            UUID characterId,
            List<UpdateCharacterInventoryRequest> requests
    ) {
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("Inventory update requests are required");
        }

        UserCharacter character = findCharacterForCurrentUser(characterId);
        List<CharacterInventoryResponse> inventory = new ArrayList<>(readInventory(character));

        for (UpdateCharacterInventoryRequest request : requests) {
            validateUpdateInventoryRequest(request);
            CharacterInventoryResponse item = findInventoryItem(inventory, request.itemName());
            CharacterInventoryResponse updatedItem = new CharacterInventoryResponse(
                    item.id() != null ? item.id() : UUID.randomUUID(),
                    character.getId(),
                    item.itemId(),
                    hasText(request.newItemName()) ? normalizedItemName(request.newItemName()) : item.itemName(),
                    request.itemDescription() != null ? request.itemDescription() : item.itemDescription(),
                    request.quantity() != null ? request.quantity() : item.quantity(),
                    request.isEquipped() != null ? request.isEquipped() : item.isEquipped(),
                    request.customDescription() != null ? request.customDescription() : item.customDescription()
            );
            replaceInventoryItem(inventory, updatedItem, request.itemName());
        }

        saveInventory(character, inventory);
        return inventory;
    }

    /**
     * Удаляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param itemName параметр, используемый при выполнении операции
     */
    @Override
    @Transactional
    public void deleteCharacterInventoryItem(UUID characterId, String itemName) {
        UserCharacter character = findCharacterForCurrentUser(characterId);
        List<CharacterInventoryResponse> inventory = new ArrayList<>(readInventory(character));
        CharacterInventoryResponse item = findInventoryItem(inventory, itemName);
        inventory.removeIf(inventoryItem -> namesEqual(inventoryItem.itemName(), item.itemName()));
        saveInventory(character, inventory);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional(readOnly = true)
    public WalletResponse getCharacterWallet(UUID characterId) {
        UserCharacter character = findCharacterForCurrentUser(characterId);
        return buildWalletResponse(character);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional
    public WalletResponse updateCharacterWallet(UUID characterId, WalletUpdateRequest request) {
        validateWalletUpdateRequest(request);
        UserCharacter character = findCharacterForCurrentUser(characterId);

        if (request.copper() != null) {
            character.setCopper(request.copper());
        }
        if (request.silver() != null) {
            character.setSilver(request.silver());
        }
        if (request.electrum() != null) {
            character.setElectrum(request.electrum());
        }
        if (request.gold() != null) {
            character.setGold(request.gold());
        }
        if (request.platinum() != null) {
            character.setPlatinum(request.platinum());
        }

        return buildWalletResponse(characterRepository.save(character));
    }

    /**
     * Удаляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     */
    @Override
    @Transactional
    public void deleteCharacter(UUID characterId) {
        User user = authService.getCurrentUser();
        UserCharacter character = characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        characterRepository.delete(character);
    }

    /**
     * Устанавливает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterResponse setCharacterClass(UUID characterId, SetCharacterClassRequest request) {
        if (request.classId() == null) {
            throw new IllegalArgumentException("classId is required");
        }

        User user = authService.getCurrentUser();
        UserCharacter character = characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        CharacterClass currentClass = character.getClassField();
        boolean classChanged = currentClass == null || !currentClass.getId().equals(request.classId());
        CharacterClass characterClass = characterClassService.getClassById(request.classId());
        ClassArchetype classArchetype = getClassArchetypeOrNull(request.classId(), request.classArchetypeId());

        character.setClassField(characterClass);
        character.setClassArchetype(classArchetype);
        if (classChanged) {
            updateClassDependentParameters(character, characterClass);
        }

        UserCharacter savedCharacter = characterRepository.save(character);

        return buildCharacterResponse(savedCharacter);
    }

    /**
     * Устанавливает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterResponse setCharacterClassArchetype(
            UUID characterId,
            SetCharacterClassArchetypeRequest request
    ) {
        if (request.classArchetypeId() == null) {
            throw new IllegalArgumentException("classArchetypeId is required");
        }

        User user = authService.getCurrentUser();
        UserCharacter character = characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        UUID classId = character.getClassField().getId();
        ClassArchetype classArchetype = characterClassService.getClassArchetypeById(
                classId,
                request.classArchetypeId()
        );

        character.setClassArchetype(classArchetype);
        UserCharacter savedCharacter = characterRepository.save(character);

        return buildCharacterResponse(savedCharacter);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param classId параметр, используемый при выполнении операции
     * @param classArchetypeId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private ClassArchetype getClassArchetypeOrNull(UUID classId, UUID classArchetypeId) {
        if (classArchetypeId == null) {
            return null;
        }

        return characterClassService.getClassArchetypeById(classId, classArchetypeId);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @param characterClass параметр, используемый при выполнении операции
     */
    private void updateClassDependentParameters(UserCharacter character, CharacterClass characterClass) {
        validateHealth(character.getMaxHealth(), character.getCurrentHealth());
        resetSavingThrows(character);
        resetSkills(character);
        clearUnavailableSpells(character, characterClass);
    }

    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     */
    private void resetSavingThrows(UserCharacter character) {
        List<CharacterSavingThrow> savingThrows = characterSavingThrowRepository.findAllByCharacterId(character.getId());
        savingThrows.forEach(savingThrow -> savingThrow.setProficiencyLevel(0));
        characterSavingThrowRepository.saveAll(savingThrows);
        character.setSavingThrowsCount(0);
    }

    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     */
    private void resetSkills(UserCharacter character) {
        List<CharacterSkill> skills = characterSkillRepository.findAllByCharacterId(character.getId());
        skills.forEach(skill -> skill.setProficiencyLevel(0));
        characterSkillRepository.saveAll(skills);
    }

    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     * @param characterClass параметр, используемый при выполнении операции
     */
    private void clearUnavailableSpells(UserCharacter character, CharacterClass characterClass) {
        if (canUseSpells(character, characterClass)) {
            return;
        }

        List<CharacterSpell> spells = characterSpellRepository.findAllByCharacterId(character.getId());
        characterSpellRepository.deleteAll(spells);
        character.setSpells(CharacterResponseMapper.serializeIds(List.of()));
    }

    /**
     * Проверяет возможность выполнения запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @param characterClass параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private boolean canUseSpells(UserCharacter character, CharacterClass characterClass) {
        if (!Boolean.TRUE.equals(characterClass.getIsSpellcaster())) {
            return false;
        }

        Integer spellcastingStartLevel = characterClass.getSpellcastingStartLevel();
        return spellcastingStartLevel != null && character.getLevel() >= spellcastingStartLevel;
    }

    /**
     * Устанавливает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
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

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Subrace getSubraceOrNull(UUID raceId, UUID subraceId) {
        if (subraceId == null) {
            return null;
        }

        return raceService.getSubrace(raceId, subraceId);
    }

    /**
     * Проверяет корректность данных для запрошенной операции.
     * @param maxHealth параметр, используемый при выполнении операции
     * @param currentHealth параметр, используемый при выполнении операции
     */
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

    /**
     * Добавляет данные для запрошенной операции.
     * @param serializedSpellIds параметр, используемый при выполнении операции
     * @param spellId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private List<UUID> addSpellId(String serializedSpellIds, UUID spellId) {
        List<UUID> spellIds = new ArrayList<>(CharacterResponseMapper.deserializeIds(serializedSpellIds));
        if (!spellIds.contains(spellId)) {
            spellIds.add(spellId);
        }
        return spellIds;
    }

    /**
     * Удаляет данные из запрошенной операции.
     * @param serializedSpellIds параметр, используемый при выполнении операции
     * @param spellId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private List<UUID> removeSpellId(String serializedSpellIds, UUID spellId) {
        List<UUID> spellIds = new ArrayList<>(CharacterResponseMapper.deserializeIds(serializedSpellIds));
        spellIds.remove(spellId);
        return spellIds;
    }

    /**
     * Находит данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private UserCharacter findCharacterForCurrentUser(UUID characterId) {
        User user = authService.getCurrentUser();
        return characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));
    }

    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private List<CharacterInventoryResponse> readInventory(UserCharacter character) {
        return CharacterResponseMapper.deserializeInventoryItems(character.getInventory()).stream()
                .map(item -> new CharacterInventoryResponse(
                        item.id(),
                        character.getId(),
                        item.itemId(),
                        item.itemName(),
                        item.itemDescription(),
                        item.quantity() != null ? item.quantity() : 1,
                        Boolean.TRUE.equals(item.isEquipped()),
                        item.customDescription()
                ))
                .toList();
    }

    /**
     * Выполняет запрошенную операцию.
     * @param character параметр, используемый при выполнении операции
     * @param inventory параметр, используемый при выполнении операции
     */
    private void saveInventory(UserCharacter character, List<CharacterInventoryResponse> inventory) {
        character.setInventory(CharacterResponseMapper.serializeInventoryItems(inventory));
        characterRepository.save(character);
    }

    /**
     * Находит данные для запрошенной операции.
     * @param inventory параметр, используемый при выполнении операции
     * @param itemName параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterInventoryResponse findInventoryItem(
            List<CharacterInventoryResponse> inventory,
            String itemName
    ) {
        CharacterInventoryResponse item = findInventoryItemOrNull(inventory, itemName);
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory item not found");
        }
        return item;
    }

    /**
     * Находит данные для запрошенной операции.
     * @param inventory параметр, используемый при выполнении операции
     * @param itemName параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterInventoryResponse findInventoryItemOrNull(
            List<CharacterInventoryResponse> inventory,
            String itemName
    ) {
        String normalizedName = normalizedItemName(itemName);
        return inventory.stream()
                .filter(item -> namesEqual(item.itemName(), normalizedName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Выполняет запрошенную операцию.
     * @param inventory параметр, используемый при выполнении операции
     * @param updatedItem параметр, используемый при выполнении операции
     */
    private void replaceInventoryItem(List<CharacterInventoryResponse> inventory, CharacterInventoryResponse updatedItem) {
        replaceInventoryItem(inventory, updatedItem, updatedItem.itemName());
    }

    /**
     * Выполняет запрошенную операцию.
     * @param inventory параметр, используемый при выполнении операции
     * @param updatedItem параметр, используемый при выполнении операции
     * @param originalItemName параметр, используемый при выполнении операции
     */
    private void replaceInventoryItem(
            List<CharacterInventoryResponse> inventory,
            CharacterInventoryResponse updatedItem,
            String originalItemName
    ) {
        for (int i = 0; i < inventory.size(); i++) {
            if (namesEqual(inventory.get(i).itemName(), originalItemName)) {
                inventory.set(i, updatedItem);
                return;
            }
        }
    }

    /**
     * Проверяет корректность данных для запрошенной операции.
     * @param request параметр, используемый при выполнении операции
     */
    private void validateAddInventoryRequest(AddCharacterInventoryRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Inventory item request is required");
        }
        normalizedItemName(request.itemName());
        if (request.quantity() == null || request.quantity() < 1) {
            throw new IllegalArgumentException("quantity must be greater than 0");
        }
    }

    /**
     * Проверяет корректность данных для запрошенной операции.
     * @param request параметр, используемый при выполнении операции
     */
    private void validateUpdateInventoryRequest(UpdateCharacterInventoryRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Inventory update request is required");
        }
        normalizedItemName(request.itemName());
        if (request.quantity() != null && request.quantity() < 1) {
            throw new IllegalArgumentException("quantity must be greater than 0");
        }
    }

    /**
     * Выполняет запрошенную операцию.
     * @param itemName параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private String normalizedItemName(String itemName) {
        if (!hasText(itemName)) {
            throw new IllegalArgumentException("itemName is required");
        }
        return itemName.trim();
    }

    /**
     * Выполняет запрошенную операцию.
     * @param first параметр, используемый при выполнении операции
     * @param second параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private boolean namesEqual(String first, String second) {
        return first != null && second != null && first.equalsIgnoreCase(second);
    }

    /**
     * Проверяет наличие данных для запрошенной операции.
     * @param value параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Возвращает безопасный список идентификаторов для запрошенной операции.
     * @param ids параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private List<UUID> requestIds(List<UUID> ids) {
        return ids != null ? ids : List.of();
    }

    /**
     * Проверяет корректность данных для запрошенной операции.
     * @param requestedIds параметр, используемый при выполнении операции
     * @param foundIds параметр, используемый при выполнении операции
     * @param message параметр, используемый при выполнении операции
     */
    private void validateReferenceIds(List<UUID> requestedIds, Set<UUID> foundIds, String message) {
        if (!foundIds.containsAll(requestedIds)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    /**
     * Формирует данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private WalletResponse buildWalletResponse(UserCharacter character) {
        return WalletResponse.builder()
                .characterWalletId(character.getId())
                .characterId(character.getId())
                .copper(nonNullCoinValue(character.getCopper()))
                .silver(nonNullCoinValue(character.getSilver()))
                .electrum(nonNullCoinValue(character.getElectrum()))
                .gold(nonNullCoinValue(character.getGold()))
                .platinum(nonNullCoinValue(character.getPlatinum()))
                .build();
    }

    /**
     * Проверяет корректность данных для запрошенной операции.
     * @param request параметр, используемый при выполнении операции
     */
    private void validateWalletUpdateRequest(WalletUpdateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Wallet update request is required");
        }
        validateCoinValue("copper", request.copper());
        validateCoinValue("silver", request.silver());
        validateCoinValue("electrum", request.electrum());
        validateCoinValue("gold", request.gold());
        validateCoinValue("platinum", request.platinum());
    }

    /**
     * Проверяет корректность данных для запрошенной операции.
     * @param coinName параметр, используемый при выполнении операции
     * @param value параметр, используемый при выполнении операции
     */
    private void validateCoinValue(String coinName, Integer value) {
        if (value != null && value < 0) {
            throw new IllegalArgumentException(coinName + " must be greater than or equal to 0");
        }
    }

    /**
     * Выполняет запрошенную операцию.
     * @param value параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Integer nonNullCoinValue(Integer value) {
        return value != null ? value : 0;
    }

    /**
     * Формирует данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterResponse buildCharacterResponse(UserCharacter character) {
        return CharacterResponseMapper.toResponse(
                character,
                characterAbilityRepository.findAllByCharacterId(character.getId()),
                characterSkillRepository.findAllByCharacterId(character.getId()),
                characterSpellRepository.findAllByCharacterId(character.getId()),
                characterSavingThrowRepository.findAllByCharacterId(character.getId())
        );
    }

    /**
     * Применяет данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @param race параметр, используемый при выполнении операции
     * @param subrace параметр, используемый при выполнении операции
     * @param characterClass параметр, используемый при выполнении операции
     * @param classArchetype параметр, используемый при выполнении операции
     */
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
