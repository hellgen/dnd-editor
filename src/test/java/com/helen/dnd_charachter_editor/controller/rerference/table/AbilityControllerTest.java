package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;
import com.helen.dnd_charachter_editor.service.reference.table.AbilityService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AbilityControllerTest {

    private final AbilityService abilityService = mock(AbilityService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new AbilityController(abilityService))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllAbilitiesReturnsBaseAbilities() throws Exception {
        List<AbilityResponse> abilities = List.of(
                new AbilityResponse(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        "STR",
                        "Сила"
                ),
                new AbilityResponse(
                        UUID.fromString("22222222-2222-2222-2222-222222222222"),
                        "DEX",
                        "Ловкость"
                )
        );
        when(abilityService.getAllAbilities()).thenReturn(abilities);

        mockMvc.perform(get("/abilities"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(abilities)));
    }

    @Test
    void getAbilityReturnsOneBaseAbility() throws Exception {
        UUID abilityId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(abilityService.getAbility(abilityId))
                .thenReturn(new AbilityResponse(abilityId, "STR", "Сила"));

        mockMvc.perform(get("/abilities/{abilityId}", abilityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.abilityId").value(abilityId.toString()))
                .andExpect(jsonPath("$.code").value("STR"))
                .andExpect(jsonPath("$.name").value("Сила"));
    }
}
