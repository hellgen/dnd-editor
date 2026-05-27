package com.helen.dnd_charachter_editor.entity.character;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "character_wallets", schema = "dnd_editor")
public class CharacterWallet {
    @Id
    @Column(name = "character_wallet_id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "character_id", nullable = false, unique = true)
    private UUID characterId;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "copper", nullable = false)
    private Integer copper;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "silver", nullable = false)
    private Integer silver;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "electrum", nullable = false)
    private Integer electrum;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "gold", nullable = false)
    private Integer gold;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "platinum", nullable = false)
    private Integer platinum;

}