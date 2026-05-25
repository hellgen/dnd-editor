package com.helen.dnd_charachter_editor.entity.reference.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "items", schema = "dnd_editor")
public class Item {
    @Id
    @Column(name = "item_id", nullable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "item_name", nullable = false, length = 100)
    private String name;

    @Size(max = 50)
    @NotNull
    @Column(name = "item_type", nullable = false, length = 50)
    private String type;

    @Size(max = 512)
    @Column(name = "item_description", length = 512)
    private String description;

    @Column(name = "weight")
    private Integer weight;

}