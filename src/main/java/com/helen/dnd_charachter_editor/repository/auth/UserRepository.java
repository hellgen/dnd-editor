package com.helen.dnd_charachter_editor.repository.auth;

import com.helen.dnd_charachter_editor.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий `UserRepository` для доступа к данным.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Находит данные для запрошенной операции.
     * @param username параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<User> findByUsername(String username);

    /**
     * Находит данные для запрошенной операции.
     * @param email параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<User> findByEmail(String email);

    /**
     * Проверяет существование данных для запрошенной операции.
     * @param username параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    boolean existsByUsername(String username);

    /**
     * Проверяет существование данных для запрошенной операции.
     * @param email параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    boolean existsByEmail(String email);
}
