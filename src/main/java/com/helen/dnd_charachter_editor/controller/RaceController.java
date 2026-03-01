package com.helen.dnd_charachter_editor.controller;

import com.helen.dnd_charachter_editor.dto.response.race_subrace.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.race_subrace.RaceResponse;
import com.helen.dnd_charachter_editor.dto.response.race_subrace.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.race_subrace.SubraceResponse;
import com.helen.dnd_charachter_editor.service.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/races")
@RequiredArgsConstructor
public class RaceController {
    private final RaceService raceService;

    @GetMapping
    public List<RaceResponse> getAllRaces() {
        return raceService.getAllRaces();
    }

    @GetMapping("/{raceId}")
    public RaceResponse getRace(@PathVariable UUID raceId) {
        return raceService.getRace(raceId);
    }

    @GetMapping("/{raceId}/subraces")
    public List<SubraceResponse> getSubraces(@PathVariable UUID raceId) {
        return raceService.getAllSubracesByRaceId(raceId);
    }

    @GetMapping("/{raceId}/description")
    public RaceDescriptionResponse getRaceDescription(@PathVariable UUID raceId) {
        return raceService.getRaceDescription(raceId);
    }

    @GetMapping("/{raceId}/subraces/{subraceId}")
    public SubraceResponse getSubrace(
            @PathVariable UUID raceId,
            @PathVariable UUID subraceId
    ) {
        return raceService.getSubrace(raceId, subraceId);
    }

    @GetMapping("/{raceId}/subraces/{subraceId}/description")
    public SubraceDescriptionResponse getSubraceDescription(
            @PathVariable UUID raceId,
            @PathVariable UUID subraceId
    ) {
        return raceService.getSubraceDescription(raceId, subraceId, description);
    }


}
