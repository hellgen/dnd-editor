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
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "classes", schema = "dnd_editor")
public class CharacterClass {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "class_id", nullable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "class_name", nullable = false, length = 100)
    private String className;

    @Size(max = 512)
    @Column(name = "class_description", length = 512)
    private String classDescription;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_spellcaster", nullable = false)
    private Boolean isSpellcaster;

    @Column(name = "spellcasting_start_level")
    private Integer spellcastingStartLevel;


}