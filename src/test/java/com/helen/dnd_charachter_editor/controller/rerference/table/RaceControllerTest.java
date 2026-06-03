package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceResponse;
import com.helen.dnd_charachter_editor.service.reference.table.RaceService;
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

/**
 * REST controller that exposes race controller test endpoints.
 */
class RaceControllerTest {

    private final RaceService raceService = mock(RaceService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new RaceController(raceService))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Returns all races returns race list.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getAllRacesReturnsRaceList() throws Exception {
        List<RaceResponse> races = List.of(
                new RaceResponse(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        "Человек",
                        80,
                        180,
                        30,
                        "Общий",
                        "Универсальная раса"
                ),
                new RaceResponse(
                        UUID.fromString("22222222-2222-2222-2222-222222222222"),
                        "Эльф",
                        750,
                        170,
                        30,
                        "Общий, Эльфийский",
                        "Долгоживущая раса"
                )
        );
        when(raceService.getAllRaces()).thenReturn(races);

        mockMvc.perform(get("/races"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(races)));
    }

    /**
     * Returns race returns one race.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getRaceReturnsOneRace() throws Exception {
        UUID raceId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(raceService.getRaceResponse(raceId))
                .thenReturn(new RaceResponse(
                        raceId,
                        "Человек",
                        80,
                        180,
                        30,
                        "Общий",
                        "Универсальная раса"
                ));

        mockMvc.perform(get("/races/{raceId}", raceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(raceId.toString()))
                .andExpect(jsonPath("$.name").value("Человек"))
                .andExpect(jsonPath("$.age").value(80))
                .andExpect(jsonPath("$.height").value(180))
                .andExpect(jsonPath("$.speed").value(30))
                .andExpect(jsonPath("$.languages").value("Общий"))
                .andExpect(jsonPath("$.description").value("Универсальная раса"));
    }

    /**
     * Returns race features returns feature list for race.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getRaceFeaturesReturnsFeatureListForRace() throws Exception {
        UUID raceId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        List<RaceFeatureResponse> features = List.of(
                new RaceFeatureResponse(
                        UUID.fromString("55555555-5555-5555-5555-555555555555"),
                        raceId,
                        "Дварф",
                        "Дварфийская устойчивость",
                        "Преимущество к спасброскам против яда"
                ),
                new RaceFeatureResponse(
                        UUID.fromString("66666666-6666-6666-6666-666666666666"),
                        raceId,
                        "Дварф",
                        "Каменное чутьё",
                        "Удвоенный бонус мастерства для проверок истории камня"
                )
        );
        when(raceService.getAllFeaturesByRaceId(raceId)).thenReturn(features);

        mockMvc.perform(get("/races/{raceId}/features", raceId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(features)));
    }

    /**
     * Returns race feature returns one feature for race.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getRaceFeatureReturnsOneFeatureForRace() throws Exception {
        UUID raceId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID featureId = UUID.fromString("55555555-5555-5555-5555-555555555555");
        when(raceService.getRaceFeatureResponse(raceId, featureId))
                .thenReturn(new RaceFeatureResponse(
                        featureId,
                        raceId,
                        "Дварф",
                        "Дварфийская устойчивость",
                        "Преимущество к спасброскам против яда"
                ));

        mockMvc.perform(get("/races/{raceId}/features/{featureId}", raceId, featureId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(featureId.toString()))
                .andExpect(jsonPath("$.raceId").value(raceId.toString()))
                .andExpect(jsonPath("$.raceName").value("Дварф"))
                .andExpect(jsonPath("$.name").value("Дварфийская устойчивость"))
                .andExpect(jsonPath("$.description").value("Преимущество к спасброскам против яда"));
    }

    /**
     * Returns subraces returns subrace list for race.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getSubracesReturnsSubraceListForRace() throws Exception {
        UUID raceId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        List<SubraceResponse> subraces = List.of(
                new SubraceResponse(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        raceId,
                        "Эльф",
                        "Высший эльф",
                        "Подраса с магическим наследием"
                ),
                new SubraceResponse(
                        UUID.fromString("44444444-4444-4444-4444-444444444444"),
                        raceId,
                        "Эльф",
                        "Лесной эльф",
                        "Подраса лесных жителей"
                )
        );
        when(raceService.getAllSubracesByRaceId(raceId)).thenReturn(subraces);

        mockMvc.perform(get("/races/{raceId}/subraces", raceId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(subraces)));
    }

    /**
     * Returns subrace features returns feature list for subrace.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getSubraceFeaturesReturnsFeatureListForSubrace() throws Exception {
        UUID raceId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID subraceId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        List<SubraceFeatureResponse> features = List.of(
                new SubraceFeatureResponse(
                        UUID.fromString("77777777-7777-7777-7777-777777777777"),
                        raceId,
                        "Эльф",
                        subraceId,
                        "Высший эльф",
                        "Заговор высшего эльфа",
                        "Вы знаете один заговор из списка волшебника"
                ),
                new SubraceFeatureResponse(
                        UUID.fromString("88888888-8888-8888-8888-888888888888"),
                        raceId,
                        "Эльф",
                        subraceId,
                        "Высший эльф",
                        "Дополнительный язык",
                        "Вы владеете одним дополнительным языком"
                )
        );
        when(raceService.getAllFeaturesBySubraceId(raceId, subraceId)).thenReturn(features);

        mockMvc.perform(get("/races/{raceId}/subraces/{subraceId}/features", raceId, subraceId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(features)));
    }

    /**
     * Returns subrace feature returns one feature for subrace.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getSubraceFeatureReturnsOneFeatureForSubrace() throws Exception {
        UUID raceId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID subraceId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID featureId = UUID.fromString("77777777-7777-7777-7777-777777777777");
        when(raceService.getSubraceFeatureResponse(raceId, subraceId, featureId))
                .thenReturn(new SubraceFeatureResponse(
                        featureId,
                        raceId,
                        "Эльф",
                        subraceId,
                        "Высший эльф",
                        "Заговор высшего эльфа",
                        "Вы знаете один заговор из списка волшебника"
                ));

        mockMvc.perform(get("/races/{raceId}/subraces/{subraceId}/features/{featureId}", raceId, subraceId, featureId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(featureId.toString()))
                .andExpect(jsonPath("$.raceId").value(raceId.toString()))
                .andExpect(jsonPath("$.raceName").value("Эльф"))
                .andExpect(jsonPath("$.subraceId").value(subraceId.toString()))
                .andExpect(jsonPath("$.subraceName").value("Высший эльф"))
                .andExpect(jsonPath("$.name").value("Заговор высшего эльфа"))
                .andExpect(jsonPath("$.description").value("Вы знаете один заговор из списка волшебника"));
    }

    /**
     * Returns subrace returns one subrace for race.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getSubraceReturnsOneSubraceForRace() throws Exception {
        UUID raceId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID subraceId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        when(raceService.getSubraceResponse(raceId, subraceId))
                .thenReturn(new SubraceResponse(
                        subraceId,
                        raceId,
                        "Эльф",
                        "Высший эльф",
                        "Подраса с магическим наследием"
                ));

        mockMvc.perform(get("/races/{raceId}/subraces/{subraceId}", raceId, subraceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(subraceId.toString()))
                .andExpect(jsonPath("$.raceId").value(raceId.toString()))
                .andExpect(jsonPath("$.raceName").value("Эльф"))
                .andExpect(jsonPath("$.name").value("Высший эльф"))
                .andExpect(jsonPath("$.description").value("Подраса с магическим наследием"));
    }
}
