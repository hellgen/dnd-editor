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
@Table(name = "races", schema = "dnd_editor")
public class Race {
    @Id
    @Column(name = "race_id", nullable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 512)
    @Column(name = "description", length = 512)
    private String description;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;

    @NotNull
    @Column(name = "speed", nullable = false)
    private Integer speed;

    @Size(max = 512)
    @Column(name = "languages", length = 512)
    private String languages;

}