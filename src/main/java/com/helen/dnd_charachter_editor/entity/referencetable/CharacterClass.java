package com.helen.dnd_charachter_editor.entity.referencetable;

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
@Table(name = "classes", schema = "dnd_editor")
public class CharacterClass {
    @Id
    @Column(name = "class_id", nullable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotNull
    @Column(name = "hit_die", nullable = false)
    private Integer hitDie;

    @Size(max = 10)
    @NotNull
    @Column(name = "primary_ability", nullable = false, length = 10)
    private String primaryAbility;

}