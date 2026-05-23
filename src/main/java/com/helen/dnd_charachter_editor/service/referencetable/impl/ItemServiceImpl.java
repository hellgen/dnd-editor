package com.helen.dnd_charachter_editor.service.referencetable.impl;

import com.helen.dnd_charachter_editor.dto.response.referencetable.ItemResponse;
import com.helen.dnd_charachter_editor.entity.referencetable.Item;
import com.helen.dnd_charachter_editor.mapper.referencetable.ItemMapper;
import com.helen.dnd_charachter_editor.repository.referncetable.ItemRepository;
import com.helen.dnd_charachter_editor.service.referencetable.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(itemMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemResponse getItem(UUID itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Предмет не найден"));

        return itemMapper.toResponse(item);
    }
}