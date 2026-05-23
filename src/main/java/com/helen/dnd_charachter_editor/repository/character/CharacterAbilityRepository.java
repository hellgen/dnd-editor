package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterAbility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CharacterAbilityRepository extends JpaRepository<CharacterAbility, UUID> {

    Optional<CharacterAbility> findByCharacterIdAndAbilityId(
            UUID characterId,
            UUID abilityId
    );
}