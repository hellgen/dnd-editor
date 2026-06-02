package com.helen.dnd_charachter_editor.service.reference.table.impl;

import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceFeature;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
import com.helen.dnd_charachter_editor.entity.reference.table.SubraceFeature;
import com.helen.dnd_charachter_editor.mapper.reference.table.RaceFeatureMapper;
import com.helen.dnd_charachter_editor.mapper.reference.table.RaceMapper;
import com.helen.dnd_charachter_editor.mapper.reference.table.SubraceFeatureMapper;
import com.helen.dnd_charachter_editor.mapper.reference.table.SubraceMapper;
import com.helen.dnd_charachter_editor.repository.reference.table.RaceFeatureRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.RaceRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.SubraceFeatureRepository;
import com.helen.dnd_charachter_editor.repository.reference.table.SubraceRepository;
import com.helen.dnd_charachter_editor.service.reference.table.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultRaceService implements RaceService {
    private final RaceRepository raceRepository;
    private final RaceFeatureRepository raceFeatureRepository;
    private final SubraceRepository subraceRepository;
    private final SubraceFeatureRepository subraceFeatureRepository;

    @Override
    public List<RaceResponse> getAllRaces() {
        return raceRepository.findAll()
                .stream()
                .map(RaceMapper::toListResponse)
                .toList();
    }

    @Override
    public RaceResponse getRaceResponse(UUID raceId) {
        Race race = getRaceByIdOrThrow(raceId);

        return RaceMapper.toListResponse(race);
    }

    @Override
    public Race getRace(UUID raceId) {
        return getRaceByIdOrThrow(raceId);
    }

    @Override
    public RaceDescriptionResponse getRaceDescription(UUID raceId) {
        Race race = getRaceByIdOrThrow(raceId);

        return RaceMapper.toRaceDescriptionResponse(race);
    }

    @Override
    public List<RaceFeatureResponse> getAllFeaturesByRaceId(UUID raceId) {
        getRaceByIdOrThrow(raceId);

        return raceFeatureRepository.findAllByRaceId(raceId)
                .stream()
                .map(RaceFeatureMapper::toResponse)
                .toList();
    }

    @Override
    public RaceFeatureResponse getRaceFeatureResponse(UUID raceId, UUID featureId) {
        RaceFeature raceFeature = getRaceFeatureByIdAndRaceIdOrThrow(raceId, featureId);

        return RaceFeatureMapper.toResponse(raceFeature);
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
    public SubraceResponse getSubraceResponse(UUID raceId, UUID subraceId) {
        Subrace subrace = getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);

        return SubraceMapper.toListResponse(subrace);
    }

    @Override
    public List<SubraceFeatureResponse> getAllFeaturesBySubraceId(UUID raceId, UUID subraceId) {
        getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);

        return subraceFeatureRepository.findAllBySubraceId(subraceId)
                .stream()
                .map(SubraceFeatureMapper::toResponse)
                .toList();
    }

    @Override
    public SubraceFeatureResponse getSubraceFeatureResponse(UUID raceId, UUID subraceId, UUID featureId) {
        getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);
        SubraceFeature subraceFeature = getSubraceFeatureByIdAndSubraceIdOrThrow(subraceId, featureId);

        return SubraceFeatureMapper.toResponse(subraceFeature);
    }

    @Override
    public Subrace getSubrace(UUID raceId, UUID subraceId) {
        return getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);
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

    private RaceFeature getRaceFeatureByIdAndRaceIdOrThrow(UUID raceId, UUID featureId) {
        return raceFeatureRepository.findByIdAndRaceId(featureId, raceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Race feature not found for this race"
                ));
    }

    private Subrace getSubraceByIdAndRaceIdOrThrow(UUID raceId, UUID subraceId) {
        return subraceRepository.findByIdAndRaceId(subraceId, raceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Subrace not found for this race"
                ));
    }

    private SubraceFeature getSubraceFeatureByIdAndSubraceIdOrThrow(UUID subraceId, UUID featureId) {
        return subraceFeatureRepository.findByIdAndSubraceId(featureId, subraceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Subrace feature not found for this subrace"
                ));
    }
}