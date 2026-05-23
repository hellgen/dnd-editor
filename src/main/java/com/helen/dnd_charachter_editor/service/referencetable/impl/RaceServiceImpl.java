package com.helen.dnd_charachter_editor.service.referencetable.impl;

import com.helen.dnd_charachter_editor.dto.response.referencetable.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.RaceResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.referencetable.SubraceResponse;
import com.helen.dnd_charachter_editor.entity.referencetable.Race;
import com.helen.dnd_charachter_editor.entity.referencetable.Subrace;
import com.helen.dnd_charachter_editor.mapper.referencetable.RaceMapper;
import com.helen.dnd_charachter_editor.mapper.referencetable.SubraceMapper;
import com.helen.dnd_charachter_editor.repository.referncetable.RaceRepository;
import com.helen.dnd_charachter_editor.repository.referncetable.SubraceRepository;
import com.helen.dnd_charachter_editor.service.referencetable.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RaceServiceImpl implements RaceService {
    private final RaceRepository raceRepository;
    private final SubraceRepository subraceRepository;

    @Override
    public List<RaceResponse> getAllRaces() {
        return raceRepository.findAll()
                .stream()
                .map(RaceMapper::toListResponse)
                .toList();
    }

    @Override
    public RaceResponse getRace(UUID raceId) {
        Race race = getRaceByIdOrThrow(raceId);

        return RaceMapper.toListResponse(race);
    }

    @Override
    public RaceDescriptionResponse getRaceDescription(UUID raceId) {
        Race race = getRaceByIdOrThrow(raceId);

        return RaceMapper.toRaceDescriptionResponse(race);
    }

    @Override
    public List<SubraceResponse> getAllSubracesByRaceId(UUID raceId) {
        getRaceByIdOrThrow(raceId);

        return subraceRepository.findAllByRaceId(raceId)
                .stream()
                .map(SubraceMapper::toListResponse)
                .toList();
    }

    @Override
    public SubraceResponse getSubrace(UUID raceId, UUID subraceId) {
        Subrace subrace = getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);

        return SubraceMapper.toListResponse(subrace);
    }

    @Override
    public SubraceDescriptionResponse getSubraceDescription(UUID raceId, UUID subraceId) {
        Subrace subrace = getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);

        return SubraceMapper.toDescriptionResponse(subrace);
    }

    private Race getRaceByIdOrThrow(UUID raceId) {
        return raceRepository.findById(raceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Race not found"
                ));
    }

    private Subrace getSubraceByIdAndRaceIdOrThrow(UUID raceId, UUID subraceId) {
        return subraceRepository.findByIdAndRaceId(subraceId, raceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Subrace not found for this race"
                ));
    }
}