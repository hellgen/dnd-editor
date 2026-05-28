package com.helen.dnd_charachter_editor.entity.reference.table;

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
@Table(name = "class_archetypes", schema = "dnd_editor")
public class ClassArchetype {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "class_archetype_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "class_id", nullable = false)
    private CharacterClass characterClass;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 512)
    @Column(name = "description", length = 512)
    private String description;

}