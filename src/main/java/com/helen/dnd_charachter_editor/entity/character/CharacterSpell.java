package com.helen.dnd_charachter_editor.entity.character;

import com.helen.dnd_charachter_editor.entity.referencetable.Spell;
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
@Table(name = "character_spells", schema = "dnd_editor")
public class CharacterSpell {
    @Id
    @Column(name = "character_spell_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "spell_id", nullable = false)
    private Spell spell;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_prepared", nullable = false)
    private Boolean isPrepared = false;

}