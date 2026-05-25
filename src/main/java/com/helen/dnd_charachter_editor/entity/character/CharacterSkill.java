package com.helen.dnd_charachter_editor.entity.character;

import com.helen.dnd_charachter_editor.entity.reference.table.Skill;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "character_skills", schema = "dnd_editor")
public class CharacterSkill {
    @Id
    @Column(name = "character_skill_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "character_id", nullable = false)
    private UserCharacter character;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "proficiency_level", nullable = false)
    private Integer proficiencyLevel;

}