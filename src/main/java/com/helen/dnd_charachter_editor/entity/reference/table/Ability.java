package com.helen.dnd_charachter_editor.entity.reference.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "abilities", schema = "dnd_editor")
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ability_id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @NotNull
    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

}