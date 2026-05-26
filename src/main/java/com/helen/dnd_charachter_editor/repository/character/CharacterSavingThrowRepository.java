package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSavingThrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CharacterSavingThrowRepository extends JpaRepository<CharacterSavingThrow, UUID> {
}
