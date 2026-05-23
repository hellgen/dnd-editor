package com.helen.dnd_charachter_editor.service.referencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.AbilityResponse;

import java.util.List;
import java.util.UUID;

public interface AbilityService {

    List<AbilityResponse> getAllAbilities();

    AbilityResponse getAbility(UUID abilityId);
}