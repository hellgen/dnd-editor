package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;

import java.util.List;
import java.util.UUID;

/**
 * Service contract for race service operations.
 */
public interface RaceService {
    /**
     * Returns all races.
     * @return result of the operation
     */
    List<RaceResponse> getAllRaces();

    /**
     * Returns race response.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    RaceResponse getRaceResponse(UUID raceId);

    /**
     * Returns race.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    Race getRace(UUID raceId);

    /**
     * Returns race description.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    RaceDescriptionResponse getRaceDescription(UUID raceId);

    /**
     * Returns all features by race id.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    List<RaceFeatureResponse> getAllFeaturesByRaceId(UUID raceId);

    /**
     * Returns race feature response.
     * @param raceId value used by this operation
     * @param featureId value used by this operation
     * @return result of the operation
     */
    RaceFeatureResponse getRaceFeatureResponse(UUID raceId, UUID featureId);

    /**
     * Returns all subraces by race id.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    List<SubraceResponse> getAllSubracesByRaceId(UUID raceId);

    /**
     * Returns subrace response.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    SubraceResponse getSubraceResponse(UUID raceId, UUID subraceId);

    /**
     * Returns all features by subrace id.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    List<SubraceFeatureResponse> getAllFeaturesBySubraceId(UUID raceId, UUID subraceId);

    /**
     * Returns subrace feature response.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @param featureId value used by this operation
     * @return result of the operation
     */
    SubraceFeatureResponse getSubraceFeatureResponse(UUID raceId, UUID subraceId, UUID featureId);

    /**
     * Returns subrace.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    Subrace getSubrace(UUID raceId, UUID subraceId);

    /**
     * Returns subrace description.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    SubraceDescriptionResponse getSubraceDescription(UUID raceId, UUID subraceId);
}
