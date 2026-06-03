package com.helen.dnd_charachter_editor.controller.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.AddCharacterSpellRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassArchetypeRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterRaceRequest;
import com.helen.dnd_charachter_editor.dto.request.character.UpdateCharacterInventoryRequest;
import com.helen.dnd_charachter_editor.dto.request.character.WalletUpdateRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterInventoryResponse;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.dto.response.character.WalletResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.service.character.CharacterService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Контроллер REST API для обработки запросов `CharacterControllerTest`.
 */
class CharacterControllerTest {

    private final CharacterService characterService = mock(CharacterService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CharacterController(characterService))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Применяет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
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

    /**
     * Обновляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
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


    /**
     * Применяет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
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

    /**
     * Обновляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
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

    /**
     * Применяет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
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

    /**
     * Обновляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
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



    /**
     * Возвращает данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void getCharacterSpellsReturnsSpellList() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(characterService.getCharacterSpells(characterId)).thenReturn(List.of(spellResponse(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Волшебная стрела"
        )));

        mockMvc.perform(get("/characters/{characterId}/spells", characterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].spellName").value("Волшебная стрела"));
    }

    /**
     * Добавляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void addCharacterSpellReturnsAddedSpell() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID spellId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        AddCharacterSpellRequest request = new AddCharacterSpellRequest(spellId);
        when(characterService.addCharacterSpell(characterId, request)).thenReturn(spellResponse(spellId, "Волшебная стрела"));

        mockMvc.perform(post("/characters/{characterId}/spells", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(spellId.toString()))
                .andExpect(jsonPath("$.spellName").value("Волшебная стрела"));
    }

    /**
     * Удаляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void deleteCharacterSpellDeletesSpell() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID spellId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        mockMvc.perform(delete("/characters/{characterId}/spells/{spellId}", characterId, spellId))
                .andExpect(status().isNoContent());

        verify(characterService).deleteCharacterSpell(characterId, spellId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void getCharacterInventoryReturnsItems() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(characterService.getCharacterInventory(characterId))
                .thenReturn(List.of(inventoryItem(characterId, "Longsword", 1)));

        mockMvc.perform(get("/characters/{characterId}/inventory", characterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemName").value("Longsword"))
                .andExpect(jsonPath("$[0].quantity").value(1));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void getCharacterInventoryItemReturnsItemByName() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(characterService.getCharacterInventoryItem(characterId, "Longsword"))
                .thenReturn(inventoryItem(characterId, "Longsword", 1));

        mockMvc.perform(get("/characters/{characterId}/inventory/item", characterId)
                        .param("itemName", "Longsword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value("Longsword"));
    }

    /**
     * Добавляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void addCharacterInventoryItemReturnsAddedItem() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        AddCharacterInventoryRequest request = new AddCharacterInventoryRequest(
                null,
                "Longsword",
                "A sharp blade",
                1,
                true,
                "Family heirloom"
        );
        when(characterService.addCharacterInventoryItem(characterId, request))
                .thenReturn(inventoryItem(characterId, "Longsword", 1));

        mockMvc.perform(post("/characters/{characterId}/inventory/item", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value("Longsword"));
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void updateCharacterInventoryItemsReturnsUpdatedItems() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        List<UpdateCharacterInventoryRequest> request = List.of(new UpdateCharacterInventoryRequest(
                "Longsword",
                "Silver Longsword",
                "Silvered blade",
                2,
                false,
                "Polished"
        ));
        when(characterService.updateCharacterInventoryItems(characterId, request))
                .thenReturn(List.of(inventoryItem(characterId, "Silver Longsword", 2)));

        mockMvc.perform(put("/characters/{characterId}/inventory/items", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemName").value("Silver Longsword"))
                .andExpect(jsonPath("$[0].quantity").value(2));
    }

    /**
     * Удаляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void deleteCharacterInventoryItemDeletesItemByName() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        mockMvc.perform(delete("/characters/{characterId}/inventory/item", characterId)
                        .param("itemName", "Longsword"))
                .andExpect(status().isNoContent());

        verify(characterService).deleteCharacterInventoryItem(characterId, "Longsword");
    }



    /**
     * Возвращает данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void getCharacterWalletReturnsWallet() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(characterService.getCharacterWallet(characterId)).thenReturn(walletResponse(characterId, 10, 5, 1, 2, 0));

        mockMvc.perform(get("/characters/{characterId}/wallet", characterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characterId").value(characterId.toString()))
                .andExpect(jsonPath("$.copper").value(10))
                .andExpect(jsonPath("$.gold").value(2));
    }

    /**
     * Обновляет данные для запрошенной операции.
     * @throws Exception если операцию невозможно выполнить
     */
    @Test
    void updateCharacterWalletReturnsUpdatedWallet() throws Exception {
        UUID characterId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        WalletUpdateRequest request = new WalletUpdateRequest(10, 5, 1, 2, 0);
        when(characterService.updateCharacterWallet(characterId, request))
                .thenReturn(walletResponse(characterId, 10, 5, 1, 2, 0));

        mockMvc.perform(put("/characters/{characterId}/wallet", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.copper").value(10))
                .andExpect(jsonPath("$.silver").value(5))
                .andExpect(jsonPath("$.electrum").value(1))
                .andExpect(jsonPath("$.gold").value(2))
                .andExpect(jsonPath("$.platinum").value(0));
    }



    /**
     * Выполняет запрошенную операцию.
     * @param spellId параметр, используемый при выполнении операции
     * @param spellName параметр, используемый при выполнении операции
     * @return результат выполнения операции
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

    /**
     * Выполняет запрошенную операцию.
     * @param characterId параметр, используемый при выполнении операции
     * @param copper параметр, используемый при выполнении операции
     * @param silver параметр, используемый при выполнении операции
     * @param electrum параметр, используемый при выполнении операции
     * @param gold параметр, используемый при выполнении операции
     * @param platinum параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private WalletResponse walletResponse(
            UUID characterId,
            Integer copper,
            Integer silver,
            Integer electrum,
            Integer gold,
            Integer platinum
    ) {
        return WalletResponse.builder()
                .characterWalletId(characterId)
                .characterId(characterId)
                .copper(copper)
                .silver(silver)
                .electrum(electrum)
                .gold(gold)
                .platinum(platinum)
                .build();
    }

    /**
     * Выполняет запрошенную операцию.
     * @param characterId параметр, используемый при выполнении операции
     * @param itemName параметр, используемый при выполнении операции
     * @param quantity параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private CharacterInventoryResponse inventoryItem(UUID characterId, String itemName, Integer quantity) {
        return new CharacterInventoryResponse(
                UUID.fromString("99999999-9999-9999-9999-999999999999"),
                characterId,
                null,
                itemName,
                null,
                quantity,
                false,
                null
        );
    }

    /**
     * Выполняет запрошенную операцию.
     * @param race параметр, используемый при выполнении операции
     * @param subrace параметр, используемый при выполнении операции
     * @param characterClass параметр, используемый при выполнении операции
     * @param classArchetype параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
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
