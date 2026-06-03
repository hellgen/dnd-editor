package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helen.dnd_charachter_editor.dto.response.reference.table.CharacterClassResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassArchetypeResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.ClassFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import com.helen.dnd_charachter_editor.service.reference.table.SpellService;
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
 * REST controller that exposes character class controller test endpoints.
 */
class CharacterClassControllerTest {

    private final CharacterClassService characterClassService = mock(CharacterClassService.class);
    private final SpellService spellService = mock(SpellService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CharacterClassController(characterClassService, spellService))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Returns all classes returns class list.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getAllClassesReturnsClassList() throws Exception {
        List<CharacterClassResponse> classes = List.of(
                new CharacterClassResponse(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        "Воин",
                        "Мастер оружия и доспехов",
                        false,
                        null
                ),
                new CharacterClassResponse(
                        UUID.fromString("22222222-2222-2222-2222-222222222222"),
                        "Волшебник",
                        "Заклинатель тайной магии",
                        true,
                        1
                )
        );
        when(characterClassService.getAllClasses()).thenReturn(classes);

        mockMvc.perform(get("/classes"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(classes)));
    }

    /**
     * Returns class by id returns one class.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getClassByIdReturnsOneClass() throws Exception {
        UUID classId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(characterClassService.getClassResponseById(classId))
                .thenReturn(new CharacterClassResponse(
                        classId,
                        "Воин",
                        "Мастер оружия и доспехов",
                        false,
                        null
                ));

        mockMvc.perform(get("/classes/{classId}", classId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(classId.toString()))
                .andExpect(jsonPath("$.className").value("Воин"))
                .andExpect(jsonPath("$.classDescription").value("Мастер оружия и доспехов"))
                .andExpect(jsonPath("$.isSpellcaster").value(false))
                .andExpect(jsonPath("$.spellcastingStartLevel").doesNotExist());
    }

    /**
     * Returns class features returns feature list.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getClassFeaturesReturnsFeatureList() throws Exception {
        UUID classId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        List<ClassFeatureResponse> features = List.of(
                new ClassFeatureResponse(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        classId,
                        "Боевой стиль",
                        "Вы выбираете боевой стиль",
                        1
                ),
                new ClassFeatureResponse(
                        UUID.fromString("44444444-4444-4444-4444-444444444444"),
                        classId,
                        "Всплеск действий",
                        "Вы можете совершить дополнительное действие",
                        2
                )
        );
        when(characterClassService.getAllFeatures(classId, null)).thenReturn(features);

        mockMvc.perform(get("/classes/{classId}/features", classId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(features)));
    }

    /**
     * Returns class features can filter by level.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getClassFeaturesCanFilterByLevel() throws Exception {
        UUID classId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        List<ClassFeatureResponse> features = List.of(
                new ClassFeatureResponse(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        classId,
                        "Боевой стиль",
                        "Вы выбираете боевой стиль",
                        1
                )
        );
        when(characterClassService.getAllFeatures(classId, 1)).thenReturn(features);

        mockMvc.perform(get("/classes/{classId}/features", classId).param("level", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(features)));
    }

    /**
     * Returns class feature by id returns one feature.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getClassFeatureByIdReturnsOneFeature() throws Exception {
        UUID classId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID featureId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        when(characterClassService.getClassFeatureById(classId, featureId))
                .thenReturn(new ClassFeatureResponse(
                        featureId,
                        classId,
                        "Боевой стиль",
                        "Вы выбираете боевой стиль",
                        1
                ));

        mockMvc.perform(get("/classes/{classId}/features/{featureId}", classId, featureId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(featureId.toString()))
                .andExpect(jsonPath("$.classId").value(classId.toString()))
                .andExpect(jsonPath("$.featureName").value("Боевой стиль"))
                .andExpect(jsonPath("$.featureDescription").value("Вы выбираете боевой стиль"))
                .andExpect(jsonPath("$.levelRequired").value(1));
    }


    /**
     * Returns class spells returns spell list.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getClassSpellsReturnsSpellList() throws Exception {
        UUID classId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(spellService.getSpellsByClassId(classId)).thenReturn(List.of(spellResponse(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Волшебная стрела"
        )));

        mockMvc.perform(get("/classes/{classId}/spells", classId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].spellName").value("Волшебная стрела"));
    }

    /**
     * Returns class archetypes returns archetype list for class.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getClassArchetypesReturnsArchetypeListForClass() throws Exception {
        UUID classId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        List<ClassArchetypeResponse> archetypes = List.of(
                new ClassArchetypeResponse(
                        UUID.fromString("55555555-5555-5555-5555-555555555555"),
                        classId,
                        "Чемпион",
                        "Архетип воина, сосредоточенный на грубой боевой мощи"
                ),
                new ClassArchetypeResponse(
                        UUID.fromString("66666666-6666-6666-6666-666666666666"),
                        classId,
                        "Мастер боевых искусств",
                        "Архетип с боевыми приёмами и превосходством"
                )
        );
        when(characterClassService.getAllArchetypes(classId)).thenReturn(archetypes);

        mockMvc.perform(get("/classes/{classId}/class-archetypes", classId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(archetypes)));
    }

    /**
     * Returns class archetype by id returns one archetype for class.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getClassArchetypeByIdReturnsOneArchetypeForClass() throws Exception {
        UUID classId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID archetypeId = UUID.fromString("55555555-5555-5555-5555-555555555555");
        when(characterClassService.getClassArchetypeResponseById(classId, archetypeId))
                .thenReturn(new ClassArchetypeResponse(
                        archetypeId,
                        classId,
                        "Чемпион",
                        "Архетип воина, сосредоточенный на грубой боевой мощи"
                ));

        mockMvc.perform(get("/classes/{classId}/class-archetypes/{classArchetypeId}", classId, archetypeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(archetypeId.toString()))
                .andExpect(jsonPath("$.classId").value(classId.toString()))
                .andExpect(jsonPath("$.name").value("Чемпион"))
                .andExpect(jsonPath("$.description").value("Архетип воина, сосредоточенный на грубой боевой мощи"));
    }

    /**
     * Returns class archetype features returns feature list for archetype.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getClassArchetypeFeaturesReturnsFeatureListForArchetype() throws Exception {
        UUID classId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID archetypeId = UUID.fromString("55555555-5555-5555-5555-555555555555");
        List<ClassArchetypeFeatureResponse> features = List.of(
                new ClassArchetypeFeatureResponse(
                        UUID.fromString("77777777-7777-7777-7777-777777777777"),
                        archetypeId,
                        "Улучшенные критические попадания",
                        "Критическое попадание происходит при результате 19 или 20",
                        3
                ),
                new ClassArchetypeFeatureResponse(
                        UUID.fromString("88888888-8888-8888-8888-888888888888"),
                        archetypeId,
                        "Выдающийся атлет",
                        "Вы добавляете половину бонуса мастерства к проверкам силы",
                        7
                )
        );
        when(characterClassService.getAllFeatures(classId, archetypeId, null)).thenReturn(features);

        mockMvc.perform(get(
                        "/classes/{classId}/class-archetypes/{classArchetypeId}/features",
                        classId,
                        archetypeId
                ))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(features)));
    }


    /**
     * Returns class archetype features can filter by level.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getClassArchetypeFeaturesCanFilterByLevel() throws Exception {
        UUID classId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID archetypeId = UUID.fromString("55555555-5555-5555-5555-555555555555");
        List<ClassArchetypeFeatureResponse> features = List.of(
                new ClassArchetypeFeatureResponse(
                        UUID.fromString("77777777-7777-7777-7777-777777777777"),
                        archetypeId,
                        "Улучшенные критические попадания",
                        "Критическое попадание происходит при результате 19 или 20",
                        3
                )
        );
        when(characterClassService.getAllFeatures(classId, archetypeId, 3)).thenReturn(features);

        mockMvc.perform(get(
                        "/classes/{classId}/class-archetypes/{classArchetypeId}/features",
                        classId,
                        archetypeId
                ).param("level", "3"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(features)));
    }

    /**
     * Returns class archetype feature by id returns one feature for archetype.
     * @throws Exception when the operation cannot be completed
     */
    @Test
    void getClassArchetypeFeatureByIdReturnsOneFeatureForArchetype() throws Exception {
        UUID classId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID archetypeId = UUID.fromString("55555555-5555-5555-5555-555555555555");
        UUID featureId = UUID.fromString("77777777-7777-7777-7777-777777777777");
        when(characterClassService.getArchetypeFeatureById(classId, archetypeId, featureId))
                .thenReturn(new ClassArchetypeFeatureResponse(
                        featureId,
                        archetypeId,
                        "Улучшенные критические попадания",
                        "Критическое попадание происходит при результате 19 или 20",
                        3
                ));

        mockMvc.perform(get(
                        "/classes/{classId}/class-archetypes/{classArchetypeId}/features/{featureId}",
                        classId,
                        archetypeId,
                        featureId
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(featureId.toString()))
                .andExpect(jsonPath("$.classArchetypeId").value(archetypeId.toString()))
                .andExpect(jsonPath("$.featureName").value("Улучшенные критические попадания"))
                .andExpect(jsonPath("$.featureDescription").value("Критическое попадание происходит при результате 19 или 20"))
                .andExpect(jsonPath("$.levelRequired").value(3));
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
