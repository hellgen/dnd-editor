package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<UserCharacter, UUID> {

    boolean existsById(@NonNull UUID id);

    Optional<UserCharacter> findByIdAndUser_Id(UUID id, UUID userId);
}
