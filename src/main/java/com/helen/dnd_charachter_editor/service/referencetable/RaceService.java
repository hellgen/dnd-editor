package com.helen.dnd_charachter_editor.service.referencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.RaceResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.SubraceResponse;

import java.util.List;
import java.util.UUID;

public interface RaceService {
    List<RaceResponse> getAllRaces();

    RaceResponse getRace(UUID raceId);

    RaceDescriptionResponse getRaceDescription(UUID raceId);

    List<SubraceResponse> getAllSubracesByRaceId(UUID raceId);

    SubraceResponse getSubrace(UUID raceId, UUID subraceId);

    SubraceDescriptionResponse getSubraceDescription(UUID raceId, UUID subraceId);
}