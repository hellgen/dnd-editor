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
 * Default service implementation for default character service operations.
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
     * Creates character.
     * @param createCharacterRequest value used by this operation
     * @return result of the operation
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

    /**
     * Returns character.
     * @param characterId value used by this operation
     * @return result of the operation
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
     * Updates character.
     * @param characterId value used by this operation
     * @param createCharacterRequest value used by this operation
     * @return result of the operation
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

    /**
     * Updates character level.
     * @param characterId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
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
     * Updates character health.
     * @param characterId value used by this operation
     * @param maxHealth value used by this operation
     * @param currentHealth value used by this operation
     * @return result of the operation
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
     * Applies character class.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @Override
    @Transactional
    public CharacterResponse applyCharacterClass(UUID characterId, SetCharacterClassRequest request) {
        return setCharacterClass(characterId, request);
    }

    /**
     * Updates character class.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @Override
    @Transactional
    public CharacterResponse updateCharacterClass(UUID characterId, SetCharacterClassRequest request) {
        return setCharacterClass(characterId, request);
    }

    /**
     * Applies character class archetype.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
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
     * Updates character class archetype.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
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
     * Applies character race.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @Override
    @Transactional
    public CharacterResponse applyCharacterRace(UUID characterId, SetCharacterRaceRequest request) {
        return setCharacterRace(characterId, request);
    }

    /**
     * Updates character race.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    @Override
    @Transactional
    public CharacterResponse updateCharacterRace(UUID characterId, SetCharacterRaceRequest request) {
        return setCharacterRace(characterId, request);
    }

    /**
     * Returns character spells.
     * @param characterId value used by this operation
     * @return result of the operation
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
     * Adds spell to character.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
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
     * Deletes spell from character.
     * @param characterId value used by this operation
     * @param spellId value used by this operation
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
     * Returns character inventory.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    @Override
    @Transactional(readOnly = true)
    public List<CharacterInventoryResponse> getCharacterInventory(UUID characterId) {
        UserCharacter character = findCharacterForCurrentUser(characterId);
        return readInventory(character);
    }

    /**
     * Returns one character inventory item by item name.
     * @param characterId value used by this operation
     * @param itemName value used by this operation
     * @return result of the operation
     */
    @Override
    @Transactional(readOnly = true)
    public CharacterInventoryResponse getCharacterInventoryItem(UUID characterId, String itemName) {
        UserCharacter character = findCharacterForCurrentUser(characterId);
        return findInventoryItem(readInventory(character), itemName);
    }

    /**
     * Adds item to character inventory.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
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
     * Updates character inventory items.
     * @param characterId value used by this operation
     * @param requests value used by this operation
     * @return result of the operation
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
     * Deletes one character inventory item by item name.
     * @param characterId value used by this operation
     * @param itemName value used by this operation
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
     * Returns character wallet.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    @Override
    @Transactional(readOnly = true)
    public WalletResponse getCharacterWallet(UUID characterId) {
        UserCharacter character = findCharacterForCurrentUser(characterId);
        return buildWalletResponse(character);
    }

    /**
     * Updates character wallet coin amounts.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
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
     * Deletes character.
     * @param characterId value used by this operation
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
     * Sets character class.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
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
        updateClassDependentParameters(character, characterClass);

        UserCharacter savedCharacter = characterRepository.save(character);

        return buildCharacterResponse(savedCharacter);
    }

    /**
     * Sets character class archetype.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
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
     * Returns class archetype or null.
     * @param classId value used by this operation
     * @param classArchetypeId value used by this operation
     * @return result of the operation
     */
    private ClassArchetype getClassArchetypeOrNull(UUID classId, UUID classArchetypeId) {
        if (classArchetypeId == null) {
            return null;
        }

        return characterClassService.getClassArchetypeById(classId, classArchetypeId);
    }

    /**
     * Updates class dependent parameters.
     * @param character value used by this operation
     * @param characterClass value used by this operation
     */
    private void updateClassDependentParameters(UserCharacter character, CharacterClass characterClass) {
        validateHealth(character.getMaxHealth(), character.getCurrentHealth());
        resetSavingThrows(character);
        resetSkills(character);
        clearUnavailableSpells(character, characterClass);
    }

    /**
     * Executes the reset saving throws operation.
     * @param character value used by this operation
     */
    private void resetSavingThrows(UserCharacter character) {
        List<CharacterSavingThrow> savingThrows = characterSavingThrowRepository.findAllByCharacterId(character.getId());
        savingThrows.forEach(savingThrow -> savingThrow.setProficiencyLevel(0));
        characterSavingThrowRepository.saveAll(savingThrows);
        character.setSavingThrowsCount(0);
    }

    /**
     * Executes the reset skills operation.
     * @param character value used by this operation
     */
    private void resetSkills(UserCharacter character) {
        List<CharacterSkill> skills = characterSkillRepository.findAllByCharacterId(character.getId());
        skills.forEach(skill -> skill.setProficiencyLevel(0));
        characterSkillRepository.saveAll(skills);
    }

    /**
     * Executes the clear unavailable spells operation.
     * @param character value used by this operation
     * @param characterClass value used by this operation
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
     * Executes the can use spells operation.
     * @param character value used by this operation
     * @param characterClass value used by this operation
     * @return result of the operation
     */
    private boolean canUseSpells(UserCharacter character, CharacterClass characterClass) {
        if (!Boolean.TRUE.equals(characterClass.getIsSpellcaster())) {
            return false;
        }

        Integer spellcastingStartLevel = characterClass.getSpellcastingStartLevel();
        return spellcastingStartLevel != null && character.getLevel() >= spellcastingStartLevel;
    }

    /**
     * Sets character race.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
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
     * Returns subrace or null.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    private Subrace getSubraceOrNull(UUID raceId, UUID subraceId) {
        if (subraceId == null) {
            return null;
        }

        return raceService.getSubrace(raceId, subraceId);
    }

    /**
     * Validates health.
     * @param maxHealth value used by this operation
     * @param currentHealth value used by this operation
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
     * Adds spell id to serialized spell identifiers.
     * @param serializedSpellIds value used by this operation
     * @param spellId value used by this operation
     * @return result of the operation
     */
    private List<UUID> addSpellId(String serializedSpellIds, UUID spellId) {
        List<UUID> spellIds = new ArrayList<>(CharacterResponseMapper.deserializeIds(serializedSpellIds));
        if (!spellIds.contains(spellId)) {
            spellIds.add(spellId);
        }
        return spellIds;
    }

    /**
     * Removes spell id from serialized spell identifiers.
     * @param serializedSpellIds value used by this operation
     * @param spellId value used by this operation
     * @return result of the operation
     */
    private List<UUID> removeSpellId(String serializedSpellIds, UUID spellId) {
        List<UUID> spellIds = new ArrayList<>(CharacterResponseMapper.deserializeIds(serializedSpellIds));
        spellIds.remove(spellId);
        return spellIds;
    }

    /**
     * Finds character that belongs to current user.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    private UserCharacter findCharacterForCurrentUser(UUID characterId) {
        User user = authService.getCurrentUser();
        return characterRepository.findByIdAndUser_Id(characterId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));
    }

    /**
     * Reads character inventory from serialized character field.
     * @param character value used by this operation
     * @return result of the operation
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
     * Saves character inventory to serialized character field.
     * @param character value used by this operation
     * @param inventory value used by this operation
     */
    private void saveInventory(UserCharacter character, List<CharacterInventoryResponse> inventory) {
        character.setInventory(CharacterResponseMapper.serializeInventoryItems(inventory));
        characterRepository.save(character);
    }

    /**
     * Finds required inventory item by name.
     * @param inventory value used by this operation
     * @param itemName value used by this operation
     * @return result of the operation
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
     * Finds optional inventory item by name.
     * @param inventory value used by this operation
     * @param itemName value used by this operation
     * @return result of the operation
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
     * Replaces inventory item by its current name.
     * @param inventory value used by this operation
     * @param updatedItem value used by this operation
     */
    private void replaceInventoryItem(List<CharacterInventoryResponse> inventory, CharacterInventoryResponse updatedItem) {
        replaceInventoryItem(inventory, updatedItem, updatedItem.itemName());
    }

    /**
     * Replaces inventory item by an original name.
     * @param inventory value used by this operation
     * @param updatedItem value used by this operation
     * @param originalItemName value used by this operation
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
     * Validates add inventory request.
     * @param request value used by this operation
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
     * Validates update inventory request.
     * @param request value used by this operation
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
     * Normalizes item name for comparisons and persistence.
     * @param itemName value used by this operation
     * @return result of the operation
     */
    private String normalizedItemName(String itemName) {
        if (!hasText(itemName)) {
            throw new IllegalArgumentException("itemName is required");
        }
        return itemName.trim();
    }

    /**
     * Compares item names ignoring case.
     * @param first value used by this operation
     * @param second value used by this operation
     * @return result of the operation
     */
    private boolean namesEqual(String first, String second) {
        return first != null && second != null && first.equalsIgnoreCase(second);
    }

    /**
     * Checks that text has visible characters.
     * @param value value used by this operation
     * @return result of the operation
     */
    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Builds character wallet response.
     * @param character value used by this operation
     * @return result of the operation
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
     * Validates wallet update request.
     * @param request value used by this operation
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
     * Validates one wallet coin value.
     * @param coinName value used by this operation
     * @param value value used by this operation
     */
    private void validateCoinValue(String coinName, Integer value) {
        if (value != null && value < 0) {
            throw new IllegalArgumentException(coinName + " must be greater than or equal to 0");
        }
    }

    /**
     * Returns non-null coin value.
     * @param value value used by this operation
     * @return result of the operation
     */
    private Integer nonNullCoinValue(Integer value) {
        return value != null ? value : 0;
    }

    /**
     * Executes the build character response operation.
     * @param character value used by this operation
     * @return result of the operation
     */
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

    /**
     * Applies main character fields.
     * @param character value used by this operation
     * @param request value used by this operation
     * @param race value used by this operation
     * @param subrace value used by this operation
     * @param characterClass value used by this operation
     * @param classArchetype value used by this operation
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
