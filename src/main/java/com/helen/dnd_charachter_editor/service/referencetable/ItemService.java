package com.helen.dnd_charachter_editor.service.referencetable;

import com.helen.dnd_charachter_editor.dto.response.referencetable.ItemResponse;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    List<ItemResponse> getAllItems();

    ItemResponse getItem(UUID itemId);
}