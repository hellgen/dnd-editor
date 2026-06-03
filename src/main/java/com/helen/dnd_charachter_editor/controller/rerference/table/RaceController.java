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
 * REST controller that exposes race controller endpoints.
 */
@RestController
@RequestMapping("/races")
@RequiredArgsConstructor
public class RaceController {
    private final RaceService raceService;

    /**
     * Returns all races.
     * @return result of the operation
     */
    @GetMapping
    public List<RaceResponse> getAllRaces() {
        return raceService.getAllRaces();
    }

    /**
     * Returns race.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{raceId}")
    public RaceResponse getRace(@PathVariable UUID raceId) {
        return raceService.getRaceResponse(raceId);
    }

    /**
     * Returns subraces.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{raceId}/subraces")
    public List<SubraceResponse> getSubraces(@PathVariable UUID raceId) {
        return raceService.getAllSubracesByRaceId(raceId);
    }

    /**
     * Returns race features.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{raceId}/features")
    public List<RaceFeatureResponse> getRaceFeatures(@PathVariable UUID raceId) {
        return raceService.getAllFeaturesByRaceId(raceId);
    }

    /**
     * Returns race feature.
     * @param raceId value used by this operation
     * @param featureId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{raceId}/features/{featureId}")
    public RaceFeatureResponse getRaceFeature(
            @PathVariable UUID raceId,
            @PathVariable UUID featureId
    ) {
        return raceService.getRaceFeatureResponse(raceId, featureId);
    }

    /**
     * Returns race description.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{raceId}/description")
    public RaceDescriptionResponse getRaceDescription(@PathVariable UUID raceId) {
        return raceService.getRaceDescription(raceId);
    }

    /**
     * Returns subrace.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{raceId}/subraces/{subraceId}")
    public SubraceResponse getSubrace(
            @PathVariable UUID raceId,
            @PathVariable UUID subraceId
    ) {
        return raceService.getSubraceResponse(raceId, subraceId);
    }

    /**
     * Returns subrace features.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{raceId}/subraces/{subraceId}/features")
    public List<SubraceFeatureResponse> getSubraceFeatures(
            @PathVariable UUID raceId,
            @PathVariable UUID subraceId
    ) {
        return raceService.getAllFeaturesBySubraceId(raceId, subraceId);
    }

    /**
     * Returns subrace feature.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @param featureId value used by this operation
     * @return result of the operation
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
     * Returns subrace description.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{raceId}/subraces/{subraceId}/description")
    public SubraceDescriptionResponse getSubraceDescription(
            @PathVariable UUID raceId,
            @PathVariable UUID subraceId
    ) {
        return raceService.getSubraceDescription(raceId, subraceId);
    }
}
