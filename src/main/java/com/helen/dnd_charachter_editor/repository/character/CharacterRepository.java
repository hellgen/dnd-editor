package com.helen.dnd_charachter_editor.repository.character;

import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `CharacterRepository` для доступа к данным.
 */
public interface CharacterRepository extends JpaRepository<UserCharacter, UUID> {

    /**
     * Проверяет существование данных для запрошенной операции.
     * @param id параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    boolean existsById(@NonNull UUID id);

    /**
     * Находит данные для запрошенной операции.
     * @param id параметр, используемый при выполнении операции
     * @param userId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<UserCharacter> findByIdAndUser_Id(UUID id, UUID userId);
}
