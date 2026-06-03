package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceResponse;
import com.helen.dnd_charachter_editor.service.reference.table.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер REST API для обработки запросов `RaceController`.
 */
@RestController
@RequestMapping("/races")
@RequiredArgsConstructor
public class RaceController {
    private final RaceService raceService;

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    @GetMapping
    public List<RaceResponse> getAllRaces() {
        return raceService.getAllRaces();
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{raceId}")
    public RaceResponse getRace(@PathVariable UUID raceId) {
        return raceService.getRaceResponse(raceId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{raceId}/subraces")
    public List<SubraceResponse> getSubraces(@PathVariable UUID raceId) {
        return raceService.getAllSubracesByRaceId(raceId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{raceId}/features")
    public List<RaceFeatureResponse> getRaceFeatures(@PathVariable UUID raceId) {
        return raceService.getAllFeaturesByRaceId(raceId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param featureId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{raceId}/features/{featureId}")
    public RaceFeatureResponse getRaceFeature(
            @PathVariable UUID raceId,
            @PathVariable UUID featureId
    ) {
        return raceService.getRaceFeatureResponse(raceId, featureId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{raceId}/description")
    public RaceDescriptionResponse getRaceDescription(@PathVariable UUID raceId) {
        return raceService.getRaceDescription(raceId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{raceId}/subraces/{subraceId}")
    public SubraceResponse getSubrace(
            @PathVariable UUID raceId,
            @PathVariable UUID subraceId
    ) {
        return raceService.getSubraceResponse(raceId, subraceId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{raceId}/subraces/{subraceId}/features")
    public List<SubraceFeatureResponse> getSubraceFeatures(
            @PathVariable UUID raceId,
            @PathVariable UUID subraceId
    ) {
        return raceService.getAllFeaturesBySubraceId(raceId, subraceId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @param featureId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{raceId}/subraces/{subraceId}/features/{featureId}")
    public SubraceFeatureResponse getSubraceFeature(
            @PathVariable UUID raceId,
            @PathVariable UUID subraceId,
            @PathVariable UUID featureId
    ) {
        return raceService.getSubraceFeatureResponse(raceId, subraceId, featureId);
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{raceId}/subraces/{subraceId}/description")
    public SubraceDescriptionResponse getSubraceDescription(
            @PathVariable UUID raceId,
            @PathVariable UUID subraceId
    ) {
        return raceService.getSubraceDescription(raceId, subraceId);
    }
}
