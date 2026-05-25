package com.helen.dnd_charachter_editor.entity.character;

import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "characters", schema = "dnd_editor")
public class UserCharacter {
    @Id
    @Column(name = "character_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subrace_id")
    private Subrace subrace;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id", nullable = false)
    private CharacterClass classField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_archetype_id")
    private ClassArchetype classArchetype;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "level", nullable = false)
    private Integer level;

    @NotNull
    @Column(name = "max_health", nullable = false)
    private Integer maxHealth;

    @NotNull
    @Column(name = "current_health", nullable = false)
    private Integer currentHealth;

    @Size(max = 512)
    @Column(name = "appearance", length = 512)
    private String appearance;

    @NotNull
    @ColumnDefault("10")
    @Column(name = "armor_class", nullable = false)
    private Integer armorClass;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

}