package com.helen.dnd_charachter_editor.controller.character;

import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSavingThrowRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSavingThrowResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterSavingThrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер REST API для обработки запросов `CharacterSavingThrowController`.
 */
@RestController
@RequestMapping("/characters/{characterId}/saving-throws")
@RequiredArgsConstructor
public class CharacterSavingThrowController {

    private final CharacterSavingThrowService characterSavingThrowService;

    /**
     * Возвращает данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping
    public List<CharacterSavingThrowResponse> getCharacterSavingThrows(
            @PathVariable UUID characterId
    ) {
        return characterSavingThrowService.getCharacterSavingThrows(characterId);
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @param characterId параметр, используемый при выполнении операции
     * @param abilityId параметр, используемый при выполнении операции
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PatchMapping("/{abilityId}")
    public CharacterSavingThrowResponse updateCharacterSavingThrow(
            @PathVariable UUID characterId,
            @PathVariable UUID abilityId,
            @Valid @RequestBody SetCharacterSavingThrowRequest request
    ) {
        return characterSavingThrowService.updateCharacterSavingThrow(characterId, abilityId, request);
    }
}
