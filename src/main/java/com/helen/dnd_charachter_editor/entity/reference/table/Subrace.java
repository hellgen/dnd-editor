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
@Table(name = "subraces", schema = "dnd_editor")
public class Subrace {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "subrace_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 512)
    @Column(name = "description", length = 512)
    private String description;

}
