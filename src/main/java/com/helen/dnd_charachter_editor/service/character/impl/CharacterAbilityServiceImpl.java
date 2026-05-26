package com.helen.dnd_charachter_editor.service.character.impl;


import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceAbilityBonus;
import com.helen.dnd_charachter_editor.entity.reference.table.SubraceAbilityBonus;
import com.helen.dnd_charachter_editor.mapper.character.CharacterAbilityMapper;
import com.helen.dnd_charachter_editor.repository.character.CharacterAbilityRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterRepository;
import com.helen.dnd_charachter_editor.repository.referencetable.AbilityRepository;
import com.helen.dnd_charachter_editor.repository.referencetable.RaceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.repository.referencetable.SubraceAbilityBonusRepository;
import com.helen.dnd_charachter_editor.service.character.CharacterAbilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CharacterAbilityServiceImpl implements CharacterAbilityService {

    private final CharacterRepository characterRepository;
    private final AbilityRepository abilityRepository;
    private final CharacterAbilityRepository characterAbilityRepository;
    private final RaceAbilityBonusRepository raceAbilityBonusRepository;
    private final SubraceAbilityBonusRepository subraceAbilityBonusRepository;
    private final CharacterAbilityMapper characterAbilityMapper;

    @Override
    @Transactional
    public CharacterAbilityResponse setCharacterAbility(UUID characterId, UUID abilityId, SetCharacterAbilityRequest request) {
        UserCharacter character = characterRepository.findById(characterId).orElseThrow(() -> new RuntimeException("Персонаж не найден"));

        Ability ability = abilityRepository.findById(abilityId).orElseThrow(() -> new RuntimeException("Характеристика не найдена"));

        validateCharacterRaceAndSubrace(character);

        CharacterAbility characterAbility = characterAbilityRepository.findByCharacterIdAndAbilityId(characterId, abilityId).orElseGet(CharacterAbility::new);

        characterAbility.setCharacter(character);
        characterAbility.setAbility(ability);
        characterAbility.setValue(request.value());

        CharacterAbility savedCharacterAbility = characterAbilityRepository.save(characterAbility);

        Integer raceBonus = getRaceBonus(character, abilityId);
        Integer subraceBonus = getSubraceBonus(character, abilityId);

        return characterAbilityMapper.toResponse(savedCharacterAbility, raceBonus, subraceBonus);
    }

    private void validateCharacterRaceAndSubrace(UserCharacter character) {
        if (character.getRace() == null) {
            throw new RuntimeException("У персонажа не выбрана раса");
        }

        if (character.getSubrace() == null) {
            return;
        }

        if (character.getSubrace().getRace() == null) {
            throw new RuntimeException("У подрасы не указана раса");
        }

        UUID characterRaceId = character.getRace().getId();
        UUID subraceRaceId = character.getSubrace().getRace().getId();

        if (!characterRaceId.equals(subraceRaceId)) {
            throw new RuntimeException("Подраса не принадлежит выбранной расе персонажа");
        }
    }

    private Integer getRaceBonus(UserCharacter character, UUID abilityId) {
        UUID raceId = character.getRace().getId();

        return raceAbilityBonusRepository.findByRaceIdAndAbilityId(raceId, abilityId).map(RaceAbilityBonus::getBonusValue).orElse(0);
    }

    private Integer getSubraceBonus(UserCharacter character, UUID abilityId) {
        if (character.getSubrace() == null) {
            return 0;
        }

        UUID subraceId = character.getSubrace().getId();

        return subraceAbilityBonusRepository.findBySubraceIdAndAbilityId(subraceId, abilityId).map(SubraceAbilityBonus::getBonusValue).orElse(0);
    }
}