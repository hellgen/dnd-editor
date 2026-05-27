package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.ItemResponse;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    List<ItemResponse> getAllItems();

    ItemResponse getItem(UUID itemId);
}