package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.RaceResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceDescriptionResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceFeatureResponse;
import com.helen.dnd_charachter_editor.dto.response.reference.table.SubraceResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса `RaceService`.
 */
public interface RaceService {
    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    List<RaceResponse> getAllRaces();

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    RaceResponse getRaceResponse(UUID raceId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Race getRace(UUID raceId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    RaceDescriptionResponse getRaceDescription(UUID raceId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<RaceFeatureResponse> getAllFeaturesByRaceId(UUID raceId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param featureId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    RaceFeatureResponse getRaceFeatureResponse(UUID raceId, UUID featureId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<SubraceResponse> getAllSubracesByRaceId(UUID raceId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    SubraceResponse getSubraceResponse(UUID raceId, UUID subraceId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<SubraceFeatureResponse> getAllFeaturesBySubraceId(UUID raceId, UUID subraceId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @param featureId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    SubraceFeatureResponse getSubraceFeatureResponse(UUID raceId, UUID subraceId, UUID featureId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Subrace getSubrace(UUID raceId, UUID subraceId);

    /**
     * Возвращает данные для запрошенной операции.
     * @param raceId параметр, используемый при выполнении операции
     * @param subraceId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    SubraceDescriptionResponse getSubraceDescription(UUID raceId, UUID subraceId);
}
