package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilitiesRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;

import java.util.List;
import java.util.UUID;

public interface CharacterAbilityService {

    CharacterAbilityResponse setCharacterAbility(
            UUID characterId,
            UUID abilityId,
            SetCharacterAbilityRequest request
    );

    List<CharacterAbilityResponse> setCharacterAbilities(
            UUID characterId,
            SetCharacterAbilitiesRequest request
    );
}
