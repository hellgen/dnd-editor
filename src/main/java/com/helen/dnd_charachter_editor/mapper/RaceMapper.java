package com.helen.dnd_charachter_editor.mapper;

import com.helen.dnd_charachter_editor.dto.response.race_subrace.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.race_subrace.RaceResponse;
import com.helen.dnd_charachter_editor.entity.Race;

public class RaceMapper {
    public static RaceResponse toListResponse(Race race) {
        return new RaceResponse(
                race.getId(),
                race.getName()
        );
    }

    public static RaceDescriptionResponse toRaceDescriptionResponse(Race race) {
        return new RaceDescriptionResponse(
                race.getId(),
                race.getAge(),
                race.getHeight(),
                race.getSpeed,
                race.getLanguages(),
                race.getDescription()
        );
    }

}
