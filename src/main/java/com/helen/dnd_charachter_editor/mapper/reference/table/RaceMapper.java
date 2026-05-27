package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;

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
                race.getSpeed(),
                race.getLanguages(),
                race.getDescription()
        );
    }
}