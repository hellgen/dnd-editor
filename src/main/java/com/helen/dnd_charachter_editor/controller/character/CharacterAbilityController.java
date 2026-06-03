package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilitiesRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterAbilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер REST API для обработки запросов `CharacterAbilityController`.
 */
@RestController
@RequestMapping("/characters/{characterId}/abilities")
@RequiredArgsConstructor
public class CharacterAbilityController {

    private final CharacterAbilityService characterAbilityService;

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping
    public List<CharacterAbilityResponse> getCharacterAbilities(
            @PathVariable UUID characterId
    ) {
        return characterAbilityService.getCharacterAbilities(characterId);
    }

    /**
     * Устанавливает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param abilityId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PostMapping("/{abilityId}")
    public CharacterAbilityResponse setCharacterAbility(
            @PathVariable UUID characterId,
            @PathVariable UUID abilityId,
            @Valid @RequestBody SetCharacterAbilityRequest request
    ) {
        return characterAbilityService.setCharacterAbility(
                characterId,
                abilityId,
                request
        );
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param abilityId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PutMapping("/{abilityId}")
    public CharacterAbilityResponse updateCharacterAbility(
            @PathVariable UUID characterId,
            @PathVariable UUID abilityId,
            @Valid @RequestBody SetCharacterAbilityRequest request
    ) {
        return characterAbilityService.setCharacterAbility(
                characterId,
                abilityId,
                request
        );
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PutMapping
    public List<CharacterAbilityResponse> updateCharacterAbilities(
            @PathVariable UUID characterId,
            @Valid @RequestBody SetCharacterAbilitiesRequest request
    ) {
        return characterAbilityService.setCharacterAbilities(characterId, request);
    }
}
