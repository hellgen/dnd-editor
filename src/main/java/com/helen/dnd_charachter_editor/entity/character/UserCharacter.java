package com.helen.dnd_charachter_editor.entity.character;

import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "characters", schema = "dnd_editor")
public class UserCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @Column(name = "inventory", length = Integer.MAX_VALUE)
    private String inventory;

    @Column(name = "abilities", length = Integer.MAX_VALUE)
    private String abilities;

    @Column(name = "spells", length = Integer.MAX_VALUE)
    private String spells;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "saving_throws_count", nullable = false)
    private Integer savingThrowsCount;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "platinum", nullable = false)
    private Integer platinum;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "gold", nullable = false)
    private Integer gold;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "electrum", nullable = false)
    private Integer electrum;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "silver", nullable = false)
    private Integer silver;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "copper", nullable = false)
    private Integer copper;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;


}