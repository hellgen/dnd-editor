package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;
import com.helen.dnd_charachter_editor.service.reference.table.AbilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/abilities")
@RequiredArgsConstructor
public class AbilityController {

    private final AbilityService abilityService;

    @GetMapping
    public List<AbilityResponse> getAllAbilities() {
        return abilityService.getAllAbilities();
    }

    @GetMapping("/{abilityId}")
    public AbilityResponse getAbility(@PathVariable UUID abilityId) {
        return abilityService.getAbility(abilityId);
    }
}