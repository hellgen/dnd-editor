package com.helen.dnd_charachter_editor.controller.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSavingThrowRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSavingThrowResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterSavingThrowService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * REST controller that exposes character saving throw controller test endpoints.
 */
class CharacterSavingThrowControllerTest {

    private final CharacterSavingThrowService characterSavingThrowService = mock(CharacterSavingThrowService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CharacterSavingThrowController(characterSavingThrowService))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Returns character saving throws returns saving throw list.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getCharacterSavingThrowsReturnsSavingThrowList() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID abilityId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        when(characterSavingThrowService.getCharacterSavingThrows(characterId))
                .thenReturn(List.of(new CharacterSavingThrowResponse(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        characterId,
                        abilityId,
                        "STRENGTH",
                        "Сила",
                        4,
                        1,
                        3,
                        7
                )));

        mockMvc.perform(get("/characters/{characterId}/saving-throws", characterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].abilityId").value(abilityId.toString()))
                .andExpect(jsonPath("$[0].abilityModifier").value(4))
                .andExpect(jsonPath("$[0].proficiencyLevel").value(1))
                .andExpect(jsonPath("$[0].totalModifier").value(7));
    }

    /**
     * Updates character saving throw returns updated modifier.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void updateCharacterSavingThrowReturnsUpdatedModifier() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID abilityId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        SetCharacterSavingThrowRequest request = new SetCharacterSavingThrowRequest(1);
        when(characterSavingThrowService.updateCharacterSavingThrow(characterId, abilityId, request))
                .thenReturn(new CharacterSavingThrowResponse(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        characterId,
                        abilityId,
                        "STRENGTH",
                        "Сила",
                        4,
                        1,
                        3,
                        7
                ));

        mockMvc.perform(patch("/characters/{characterId}/saving-throws/{abilityId}", characterId, abilityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.proficiencyLevel").value(1))
                .andExpect(jsonPath("$.proficiencyBonus").value(3))
                .andExpect(jsonPath("$.totalModifier").value(7));
    }
}
