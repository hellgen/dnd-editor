package com.helen.dnd_charachter_editor.controller.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassArchetypeRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterRaceRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CharacterControllerTest {

    private final CharacterService characterService = mock(CharacterService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CharacterController(characterService))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void applyCharacterClassReturnsCharacterWithSelectedClassAndArchetype() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        SetCharacterClassRequest request = new SetCharacterClassRequest(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                UUID.fromString("33333333-3333-3333-3333-333333333333")
        );
        when(characterService.applyCharacterClass(characterId, request))
                .thenReturn(characterResponse("Эльф", "Высший эльф", "Воин", "Чемпион"));

        mockMvc.perform(post("/characters/{characterId}/class", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characterClass").value("Воин"))
                .andExpect(jsonPath("$.classArchetype").value("Чемпион"));
    }

    @Test
    void updateCharacterClassReturnsCharacterWithChangedClassAndNoArchetype() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        SetCharacterClassRequest request = new SetCharacterClassRequest(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                null
        );
        when(characterService.updateCharacterClass(characterId, request))
                .thenReturn(characterResponse("Эльф", "Высший эльф", "Волшебник", null));

        mockMvc.perform(put("/characters/{characterId}/class", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characterClass").value("Волшебник"))
                .andExpect(jsonPath("$.classArchetype").doesNotExist());
    }


    @Test
    void applyCharacterClassArchetypeReturnsCharacterWithSelectedArchetype() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        SetCharacterClassArchetypeRequest request = new SetCharacterClassArchetypeRequest(
                UUID.fromString("33333333-3333-3333-3333-333333333333")
        );
        when(characterService.applyCharacterClassArchetype(characterId, request))
                .thenReturn(characterResponse("Эльф", "Высший эльф", "Воин", "Чемпион"));

        mockMvc.perform(post("/characters/{characterId}/class-archetype", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characterClass").value("Воин"))
                .andExpect(jsonPath("$.classArchetype").value("Чемпион"));
    }

    @Test
    void updateCharacterClassArchetypeReturnsCharacterWithChangedArchetype() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        SetCharacterClassArchetypeRequest request = new SetCharacterClassArchetypeRequest(
                UUID.fromString("44444444-4444-4444-4444-444444444444")
        );
        when(characterService.updateCharacterClassArchetype(characterId, request))
                .thenReturn(characterResponse("Эльф", "Высший эльф", "Воин", "Мастер боевых искусств"));

        mockMvc.perform(put("/characters/{characterId}/class-archetype", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characterClass").value("Воин"))
                .andExpect(jsonPath("$.classArchetype").value("Мастер боевых искусств"));
    }

    @Test
    void applyCharacterRaceReturnsCharacterWithSelectedRaceAndSubrace() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        SetCharacterRaceRequest request = new SetCharacterRaceRequest(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                UUID.fromString("33333333-3333-3333-3333-333333333333")
        );
        when(characterService.applyCharacterRace(characterId, request))
                .thenReturn(characterResponse("Эльф", "Высший эльф", "Воин", null));

        mockMvc.perform(post("/characters/{characterId}/race", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.race").value("Эльф"))
                .andExpect(jsonPath("$.subrace").value("Высший эльф"));
    }

    @Test
    void updateCharacterRaceReturnsCharacterWithChangedRaceAndNoSubrace() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        SetCharacterRaceRequest request = new SetCharacterRaceRequest(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                null
        );
        when(characterService.updateCharacterRace(characterId, request))
                .thenReturn(characterResponse("Человек", null, "Воин", null));

        mockMvc.perform(put("/characters/{characterId}/race", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.race").value("Человек"))
                .andExpect(jsonPath("$.subrace").doesNotExist());
    }

    private CharacterResponse characterResponse(
            String race,
            String subrace,
            String characterClass,
            String classArchetype
    ) {
        return new CharacterResponse(
                "Лиа",
                race,
                subrace,
                characterClass,
                classArchetype,
                1,
                10,
                10,
                null,
                10,
                List.of(),
                0,
                0,
                0,
                0,
                0,
                List.of(),
                List.of(),
                List.of(),
                0,
                null,
                null
        );
    }
}
