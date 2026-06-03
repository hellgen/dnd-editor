package com.helen.dnd_charachter_editor.controller.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilitiesRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterAbilityValueRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterAbilityResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterAbilityService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Контроллер REST API для обработки запросов `CharacterAbilityControllerTest`.
 */
class CharacterAbilityControllerTest {

    private final CharacterAbilityService characterAbilityService = mock(CharacterAbilityService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CharacterAbilityController(characterAbilityService))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Возвращает данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void getCharacterAbilitiesReturnsFinalValuesWithCurrentRaceBonuses() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID abilityId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        when(characterAbilityService.getCharacterAbilities(characterId))
                .thenReturn(List.of(new CharacterAbilityResponse(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        characterId,
                        abilityId,
                        "STR",
                        "Сила",
                        15,
                        2,
                        1,
                        18,
                        4
                )));

        mockMvc.perform(get("/characters/{characterId}/abilities", characterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].baseValue").value(15))
                .andExpect(jsonPath("$[0].raceBonus").value(2))
                .andExpect(jsonPath("$[0].subraceBonus").value(1))
                .andExpect(jsonPath("$[0].totalValue").value(18));
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void updateCharacterAbilityReturnsFinalValueWithBonuses() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID abilityId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        SetCharacterAbilityRequest request = new SetCharacterAbilityRequest(15);

        when(characterAbilityService.setCharacterAbility(characterId, abilityId, request))
                .thenReturn(new CharacterAbilityResponse(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        characterId,
                        abilityId,
                        "STR",
                        "Сила",
                        15,
                        2,
                        1,
                        18,
                        4
                ));

        mockMvc.perform(put("/characters/{characterId}/abilities/{abilityId}", characterId, abilityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseValue").value(15))
                .andExpect(jsonPath("$.raceBonus").value(2))
                .andExpect(jsonPath("$.subraceBonus").value(1))
                .andExpect(jsonPath("$.totalValue").value(18));
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void updateCharacterAbilitiesReturnsSeveralFinalValuesWithBonuses() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID strengthId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID dexterityId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        SetCharacterAbilitiesRequest request = new SetCharacterAbilitiesRequest(List.of(
                new SetCharacterAbilityValueRequest(strengthId, 15),
                new SetCharacterAbilityValueRequest(dexterityId, 14)
        ));

        when(characterAbilityService.setCharacterAbilities(characterId, request))
                .thenReturn(List.of(
                        new CharacterAbilityResponse(
                                UUID.fromString("44444444-4444-4444-4444-444444444444"),
                                characterId,
                                strengthId,
                                "STR",
                                "Сила",
                                15,
                                2,
                                1,
                                18,
                                4
                        ),
                        new CharacterAbilityResponse(
                                UUID.fromString("55555555-5555-5555-5555-555555555555"),
                                characterId,
                                dexterityId,
                                "DEX",
                                "Ловкость",
                                14,
                                0,
                                2,
                                16,
                                3
                        )
                ));

        mockMvc.perform(put("/characters/{characterId}/abilities", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].baseValue").value(15))
                .andExpect(jsonPath("$[0].totalValue").value(18))
                .andExpect(jsonPath("$[1].baseValue").value(14))
                .andExpect(jsonPath("$[1].totalValue").value(16));
    }
}
