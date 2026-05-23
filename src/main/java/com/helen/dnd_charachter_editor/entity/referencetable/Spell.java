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
@Table(name = "spells", schema = "dnd_editor")
public class Spell {
    @Id
    @Column(name = "spell_id", nullable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "spell_name", nullable = false, length = 100)
    private String spellName;

    @NotNull
    @Column(name = "spell_level", nullable = false)
    private Integer spellLevel;

    @Size(max = 100)
    @NotNull
    @Column(name = "spell_school", nullable = false, length = 100)
    private String spellSchool;

    @Size(max = 100)
    @NotNull
    @Column(name = "casting_time", nullable = false, length = 100)
    private String castingTime;

    @Size(max = 100)
    @NotNull
    @Column(name = "spell_range", nullable = false, length = 100)
    private String spellRange;

    @Size(max = 100)
    @NotNull
    @Column(name = "components", nullable = false, length = 100)
    private String components;

    @Size(max = 100)
    @NotNull
    @Column(name = "duration", nullable = false, length = 100)
    private String duration;

    @Size(max = 2048)
    @Column(name = "spell_description", length = 2048)
    private String spellDescription;

}