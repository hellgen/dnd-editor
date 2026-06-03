package com.helen.dnd_charachter_editor.mapper.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceFeatureResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.RaceFeature;

/**
 * Маппер `RaceFeatureMapper` для преобразования данных между слоями приложения.
 */
public class RaceFeatureMapper {

    /**
     * Преобразует данные для запрошенной операции.
     * @param raceFeature параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static RaceFeatureResponse toResponse(RaceFeature raceFeature) {
        return new RaceFeatureResponse(
                raceFeature.getId(),
                raceFeature.getRace().getId(),
                raceFeature.getRace().getName(),
                raceFeature.getFeatureName(),
                raceFeature.getFeatureDescription()
        );
    }
}
