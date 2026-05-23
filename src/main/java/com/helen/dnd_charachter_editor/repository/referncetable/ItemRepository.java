package com.helen.dnd_charachter_editor.repository.referncetable;

import com.helen.dnd_charachter_editor.entity.referencetable.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}