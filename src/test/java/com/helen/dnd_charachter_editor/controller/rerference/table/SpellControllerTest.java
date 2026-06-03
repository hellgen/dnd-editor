package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.service.reference.table.SpellService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * REST controller that exposes spell controller test endpoints.
 */
class SpellControllerTest {

    private final SpellService spellService = mock(SpellService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new SpellController(spellService))
            .build();

    /**
     * Returns all spells.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getAllSpellsReturnsSpellList() throws Exception {
        UUID spellId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(spellService.getAllSpells()).thenReturn(List.of(spellResponse(spellId, "Волшебная стрела")));

        mockMvc.perform(get("/spells"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(spellId.toString()))
                .andExpect(jsonPath("$[0].spellName").value("Волшебная стрела"));
    }

    /**
     * Returns spell by id.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getSpellByIdReturnsSpell() throws Exception {
        UUID spellId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(spellService.getSpellResponse(spellId)).thenReturn(spellResponse(spellId, "Волшебная стрела"));

        mockMvc.perform(get("/spells/{spellId}", spellId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(spellId.toString()))
                .andExpect(jsonPath("$.spellName").value("Волшебная стрела"));
    }

    /**
     * Executes spell response operation.
     * @param spellId value used by this operation
     * @param spellName value used by this operation
     * @return result of the operation
     */
    private SpellResponse spellResponse(UUID spellId, String spellName) {
        return new SpellResponse(
                spellId,
                spellName,
                1,
                "Воплощение",
                "1 действие",
                "120 футов",
                "В, С",
                "Мгновенная",
                "Описание"
        );
    }
}
