package com.helen.dnd_charachter_editor.service.reference.table.impl;

import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.mapper.reference.table.AbilityMapper;
import com.helen.dnd_charachter_editor.repository.reference.table.AbilityRepository;
import com.helen.dnd_charachter_editor.service.reference.table.AbilityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Реализация сервиса `DefaultAbilityService`.
 */
@Service
@RequiredArgsConstructor
public class DefaultAbilityService implements AbilityService {

    private final AbilityRepository abilityRepository;
    private final AbilityMapper abilityMapper;

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    @Override
    @Transactional(readOnly = true)
    public List<AbilityResponse> getAllAbilities() {
        return abilityRepository.findAll()
                .stream()
                .map(abilityMapper::toResponse)
                .toList();
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param abilityId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @Override
    @Transactional(readOnly = true)
    public AbilityResponse getAbility(UUID abilityId) {
        Ability ability = abilityRepository.findById(abilityId)
                .orElseThrow(() -> new EntityNotFoundException("Характеристика не найдена"));

        return abilityMapper.toResponse(ability);
    }
}
