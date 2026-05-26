package com.helen.dnd_charachter_editor.service.reference.table.impl;

import com.helen.dnd_charachter_editor.dto.response.reference.table.AbilityResponse;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.mapper.reference.table.AbilityMapper;
import com.helen.dnd_charachter_editor.repository.referencetable.AbilityRepository;
import com.helen.dnd_charachter_editor.service.reference.table.AbilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AbilityServiceImpl implements AbilityService {

    private final AbilityRepository abilityRepository;
    private final AbilityMapper abilityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AbilityResponse> getAllAbilities() {
        return abilityRepository.findAll()
                .stream()
                .map(abilityMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AbilityResponse getAbility(UUID abilityId) {
        Ability ability = abilityRepository.findById(abilityId)
                .orElseThrow(() -> new RuntimeException("Характеристика не найдена"));

        return abilityMapper.toResponse(ability);
    }
}