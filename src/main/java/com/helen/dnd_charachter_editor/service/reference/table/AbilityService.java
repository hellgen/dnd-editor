package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service contract for ability service operations.
 */
public interface AbilityService {

    /**
     * Returns all abilities.
     * @return result of the operation
     */
    List<AbilityResponse> getAllAbilities();

    /**
     * Returns ability.
     * @param abilityId value used by this operation
     * @return result of the operation
     */
    AbilityResponse getAbility(UUID abilityId);
}
