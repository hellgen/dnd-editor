package com.helen.dnd_charachter_editor.service.reference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.ItemResponse;

import java.util.List;
import java.util.UUID;

/**
 * Контракт сервиса `ItemService`.
 */
public interface ItemService {

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    List<ItemResponse> getAllItems();

    /**
     * Возвращает данные для запрошенной операции.
     * @param itemId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    ItemResponse getItem(UUID itemId);
}
