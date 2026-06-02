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

public interface RaceService {
    List<RaceResponse> getAllRaces();

    RaceResponse getRaceResponse(UUID raceId);

    Race getRace(UUID raceId);

    RaceDescriptionResponse getRaceDescription(UUID raceId);

    List<RaceFeatureResponse> getAllFeaturesByRaceId(UUID raceId);

    RaceFeatureResponse getRaceFeatureResponse(UUID raceId, UUID featureId);

    List<SubraceResponse> getAllSubracesByRaceId(UUID raceId);

    SubraceResponse getSubraceResponse(UUID raceId, UUID subraceId);

    List<SubraceFeatureResponse> getAllFeaturesBySubraceId(UUID raceId, UUID subraceId);

    SubraceFeatureResponse getSubraceFeatureResponse(UUID raceId, UUID subraceId, UUID featureId);

    Subrace getSubrace(UUID raceId, UUID subraceId);

    SubraceDescriptionResponse getSubraceDescription(UUID raceId, UUID subraceId);
}