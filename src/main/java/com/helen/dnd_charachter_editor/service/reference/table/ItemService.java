package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.ItemResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service contract for item service operations.
 */
public interface ItemService {

    /**
     * Returns all items.
     * @return result of the operation
     */
    List<ItemResponse> getAllItems();

    /**
     * Returns item.
     * @param itemId value used by this operation
     * @return result of the operation
     */
    ItemResponse getItem(UUID itemId);
}
