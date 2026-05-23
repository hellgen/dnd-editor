package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CharacterWalletRepository extends JpaRepository<CharacterWallet, UUID> {

    Optional<CharacterWallet> findByCharacterId(UUID characterId);

    boolean existsByCharacterId(UUID characterId);
}