package com.helen.dnd_charachter_editor.entity.character;

import com.helen.dnd_charachter_editor.entity.reference.table.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "character_inventory", schema = "dnd_editor")
public class CharacterInventory {
    @Id
    @Column(name = "character_inventory_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "character_id", nullable = false)
    private UserCharacter character;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "is_equipped", nullable = false)
    private Boolean isEquipped = false;

    @Size(max = 512)
    @Column(name = "custom_description", length = 512)
    private String customDescription;

}