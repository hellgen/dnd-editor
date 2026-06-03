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

/**
 * Default service implementation for default race service operations.
 */
@Service
@RequiredArgsConstructor
public class DefaultRaceService implements RaceService {
    private final RaceRepository raceRepository;
    private final RaceFeatureRepository raceFeatureRepository;
    private final SubraceRepository subraceRepository;
    private final SubraceFeatureRepository subraceFeatureRepository;

    /**
     * Returns all races.
     * @return result of the operation
     */
    @Override
    public List<RaceResponse> getAllRaces() {
        return raceRepository.findAll()
                .stream()
                .map(RaceMapper::toListResponse)
                .toList();
    }

    /**
     * Returns race response.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    @Override
    public RaceResponse getRaceResponse(UUID raceId) {
        Race race = getRaceByIdOrThrow(raceId);

        return RaceMapper.toListResponse(race);
    }

    /**
     * Returns race.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    @Override
    public Race getRace(UUID raceId) {
        return getRaceByIdOrThrow(raceId);
    }

    /**
     * Returns race description.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    @Override
    public RaceDescriptionResponse getRaceDescription(UUID raceId) {
        Race race = getRaceByIdOrThrow(raceId);

        return RaceMapper.toRaceDescriptionResponse(race);
    }

    /**
     * Returns all features by race id.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    @Override
    public List<RaceFeatureResponse> getAllFeaturesByRaceId(UUID raceId) {
        getRaceByIdOrThrow(raceId);

        return raceFeatureRepository.findAllByRaceId(raceId)
                .stream()
                .map(RaceFeatureMapper::toResponse)
                .toList();
    }

    /**
     * Returns race feature response.
     * @param raceId value used by this operation
     * @param featureId value used by this operation
     * @return result of the operation
     */
    @Override
    public RaceFeatureResponse getRaceFeatureResponse(UUID raceId, UUID featureId) {
        RaceFeature raceFeature = getRaceFeatureByIdAndRaceIdOrThrow(raceId, featureId);

        return RaceFeatureMapper.toResponse(raceFeature);
    }

    /**
     * Returns all subraces by race id.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    @Override
    public List<SubraceResponse> getAllSubracesByRaceId(UUID raceId) {
        getRaceByIdOrThrow(raceId);

        return subraceRepository.findAllByRaceId(raceId)
                .stream()
                .map(SubraceMapper::toListResponse)
                .toList();
    }

    /**
     * Returns subrace response.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    @Override
    public SubraceResponse getSubraceResponse(UUID raceId, UUID subraceId) {
        Subrace subrace = getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);

        return SubraceMapper.toListResponse(subrace);
    }

    /**
     * Returns all features by subrace id.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    @Override
    public List<SubraceFeatureResponse> getAllFeaturesBySubraceId(UUID raceId, UUID subraceId) {
        getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);

        return subraceFeatureRepository.findAllBySubraceId(subraceId)
                .stream()
                .map(SubraceFeatureMapper::toResponse)
                .toList();
    }

    /**
     * Returns subrace feature response.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @param featureId value used by this operation
     * @return result of the operation
     */
    @Override
    public SubraceFeatureResponse getSubraceFeatureResponse(UUID raceId, UUID subraceId, UUID featureId) {
        getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);
        SubraceFeature subraceFeature = getSubraceFeatureByIdAndSubraceIdOrThrow(subraceId, featureId);

        return SubraceFeatureMapper.toResponse(subraceFeature);
    }

    /**
     * Returns subrace.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    @Override
    public Subrace getSubrace(UUID raceId, UUID subraceId) {
        return getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);
    }

    /**
     * Returns subrace description.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    @Override
    public SubraceDescriptionResponse getSubraceDescription(UUID raceId, UUID subraceId) {
        Subrace subrace = getSubraceByIdAndRaceIdOrThrow(raceId, subraceId);

        return SubraceMapper.toDescriptionResponse(subrace);
    }

    /**
     * Returns race by id or throw.
     * @param raceId value used by this operation
     * @return result of the operation
     */
    private Race getRaceByIdOrThrow(UUID raceId) {
        return raceRepository.findById(raceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Race not found"
                ));
    }

    /**
     * Returns race feature by id and race id or throw.
     * @param raceId value used by this operation
     * @param featureId value used by this operation
     * @return result of the operation
     */
    private RaceFeature getRaceFeatureByIdAndRaceIdOrThrow(UUID raceId, UUID featureId) {
        return raceFeatureRepository.findByIdAndRaceId(featureId, raceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Race feature not found for this race"
                ));
    }

    /**
     * Returns subrace by id and race id or throw.
     * @param raceId value used by this operation
     * @param subraceId value used by this operation
     * @return result of the operation
     */
    private Subrace getSubraceByIdAndRaceIdOrThrow(UUID raceId, UUID subraceId) {
        return subraceRepository.findByIdAndRaceId(subraceId, raceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Subrace not found for this race"
                ));
    }

    /**
     * Returns subrace feature by id and subrace id or throw.
     * @param subraceId value used by this operation
     * @param featureId value used by this operation
     * @return result of the operation
     */
    private SubraceFeature getSubraceFeatureByIdAndSubraceIdOrThrow(UUID subraceId, UUID featureId) {
        return subraceFeatureRepository.findByIdAndSubraceId(featureId, subraceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Subrace feature not found for this subrace"
                ));
    }
}
