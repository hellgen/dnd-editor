package com.helen.dnd_charachter_editor.repository.auth;

import com.helen.dnd_charachter_editor.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing user repository data.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds by username.
     * @param username value used by this operation
     * @return result of the operation
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds by email.
     * @param email value used by this operation
     * @return result of the operation
     */
    Optional<User> findByEmail(String email);

    /**
     * Executes the exists by username operation.
     * @param username value used by this operation
     * @return result of the operation
     */
    boolean existsByUsername(String username);

    /**
     * Executes the exists by email operation.
     * @param email value used by this operation
     * @return result of the operation
     */
    boolean existsByEmail(String email);
}
