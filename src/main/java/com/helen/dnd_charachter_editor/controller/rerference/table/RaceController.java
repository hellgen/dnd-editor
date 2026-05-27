package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceResponse;
import com.helen.dnd_charachter_editor.service.reference.table.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return raceService.getRaceResponse(raceId);
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
        return raceService.getSubraceResponse(raceId, subraceId);
    }

    @GetMapping("/{raceId}/subraces/{subraceId}/description")
    public SubraceDescriptionResponse getSubraceDescription(
            @PathVariable UUID raceId,
            @PathVariable UUID subraceId
    ) {
        return raceService.getSubraceDescription(raceId, subraceId);
    }
}