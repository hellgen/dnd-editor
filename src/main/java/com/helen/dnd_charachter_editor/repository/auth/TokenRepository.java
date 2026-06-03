package com.helen.dnd_charachter_editor.repository.auth;

import com.helen.dnd_charachter_editor.entity.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing token repository data.
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
            SELECT t FROM Token t inner join User u
            on t.user.id = u.id
            where t.user.id = :userId and t.loggedOut = false
            """)

    /**
     * Finds all access token by user.
     * @param userId value used by this operation
     * @return result of the operation
     */
    List<Token> findAllAccessTokenByUser(Long userId);

    /**
     * Finds by access token.
     * @param accessToken value used by this operation
     * @return result of the operation
     */
    Optional<Token> findByAccessToken(String accessToken);

    /**
     * Finds by refresh token.
     * @param refreshToken value used by this operation
     * @return result of the operation
     */
    Optional<Token> findByRefreshToken(String refreshToken);
}
