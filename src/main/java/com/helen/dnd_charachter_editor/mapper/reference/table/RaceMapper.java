package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;

/**
 * Mapper that converts race mapper values between layers.
 */
public class RaceMapper {
    /**
     * Converts list response.
     * @param race value used by this operation
     * @return result of the operation
     */
    public static RaceResponse toListResponse(Race race) {
        return new RaceResponse(
                race.getId(),
                race.getName(),
                race.getAge(),
                race.getHeight(),
                race.getSpeed(),
                race.getLanguages(),
                race.getDescription()
        );
    }

    /**
     * Converts race description response.
     * @param race value used by this operation
     * @return result of the operation
     */
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
