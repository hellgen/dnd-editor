package com.helen.dnd_charachter_editor.entity.referencetable;

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
@Table(name = "subrace_ability_bonuses", schema = "dnd_editor")
public class SubraceAbilityBonus {
    @Id
    @Column(name = "subrace_ability_bonus_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "subrace_id", nullable = false)
    private Subrace subrace;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ability_id", nullable = false)
    private Ability ability;

    @NotNull
    @Column(name = "bonus_value", nullable = false)
    private Integer bonusValue;

}