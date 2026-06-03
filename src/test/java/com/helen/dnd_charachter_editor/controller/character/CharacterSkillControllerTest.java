package com.helen.dnd_charachter_editor.controller.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterSkillRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterSkillResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterSkillService;
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
 * REST controller that exposes character skill controller test endpoints.
 */
class CharacterSkillControllerTest {

    private final CharacterSkillService characterSkillService = mock(CharacterSkillService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CharacterSkillController(characterSkillService))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Returns character skills returns skill list.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getCharacterSkillsReturnsSkillList() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID skillId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        when(characterSkillService.getCharacterSkills(characterId))
                .thenReturn(List.of(new CharacterSkillResponse(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        characterId,
                        skillId,
                        "Атлетика",
                        "STR",
                        3,
                        1,
                        2,
                        5
                )));

        mockMvc.perform(get("/characters/{characterId}/skills", characterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].skillId").value(skillId.toString()))
                .andExpect(jsonPath("$[0].abilityModifier").value(3))
                .andExpect(jsonPath("$[0].proficiencyLevel").value(1))
                .andExpect(jsonPath("$[0].totalModifier").value(5));
    }

    /**
     * Updates character skill returns double proficiency modifier.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void updateCharacterSkillReturnsDoubleProficiencyModifier() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID skillId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        SetCharacterSkillRequest request = new SetCharacterSkillRequest(2);
        when(characterSkillService.updateCharacterSkill(characterId, skillId, request))
                .thenReturn(new CharacterSkillResponse(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        characterId,
                        skillId,
                        "Атлетика",
                        "STR",
                        3,
                        2,
                        2,
                        7
                ));

        mockMvc.perform(put("/characters/{characterId}/skills/{skillId}", characterId, skillId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.proficiencyLevel").value(2))
                .andExpect(jsonPath("$.proficiencyBonus").value(2))
                .andExpect(jsonPath("$.totalModifier").value(7));
    }
}
