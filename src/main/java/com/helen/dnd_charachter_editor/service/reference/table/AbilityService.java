package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;

import java.util.List;
import java.util.UUID;

public interface AbilityService {

    List<AbilityResponse> getAllAbilities();

    AbilityResponse getAbility(UUID abilityId);
}