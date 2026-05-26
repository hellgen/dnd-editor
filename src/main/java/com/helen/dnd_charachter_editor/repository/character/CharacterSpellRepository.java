package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSpell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CharacterSpellRepository extends JpaRepository<CharacterSpell, UUID> {
    List<CharacterSpell> findAllByCharacterId(UUID characterId);
}
