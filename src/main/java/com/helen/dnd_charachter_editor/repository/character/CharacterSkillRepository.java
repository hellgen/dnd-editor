package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CharacterSkillRepository extends JpaRepository<CharacterSkill, UUID> {
    List<CharacterSkill> findAllByCharacterId(UUID characterId);
}
