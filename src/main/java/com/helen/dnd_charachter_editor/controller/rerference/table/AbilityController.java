package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;
import com.helen.dnd_charachter_editor.service.reference.table.AbilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller that exposes ability controller endpoints.
 */
@RestController
@RequestMapping("/abilities")
@RequiredArgsConstructor
public class AbilityController {

    private final AbilityService abilityService;

    /**
     * Returns all abilities.
     * @return result of the operation
     */
    @GetMapping
    public List<AbilityResponse> getAllAbilities() {
        return abilityService.getAllAbilities();
    }

    /**
     * Returns ability.
     * @param abilityId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{abilityId}")
    public AbilityResponse getAbility(@PathVariable UUID abilityId) {
        return abilityService.getAbility(abilityId);
    }
}
