package com.helen.dnd_charachter_editor.entity.character;

import com.helen.dnd_charachter_editor.entity.referencetable.Ability;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "character_abilities", schema = "dnd_editor")
public class CharacterAbility {
    @Id
    @Column(name = "character_ability_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ability_id", nullable = false)
    private Ability ability;

    @NotNull
    @Column(name = "value", nullable = false)
    private Integer value;

}