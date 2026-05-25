package com.helen.dnd_charachter_editor.repository.refernce.table;

import com.helen.dnd_charachter_editor.entity.reference.table.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}