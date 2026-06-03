package com.helen.dnd_charachter_editor.repository.auth;

import com.helen.dnd_charachter_editor.entity.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий `TokenRepository` для доступа к данным.
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
            SELECT t FROM Token t inner join User u
            on t.user.id = u.id
            where t.user.id = :userId and t.loggedOut = false
            """)

    /**
     * Находит данные для запрошенной операции.
     * @param userId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    List<Token> findAllAccessTokenByUser(Long userId);

    /**
     * Находит данные для запрошенной операции.
     * @param accessToken параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<Token> findByAccessToken(String accessToken);

    /**
     * Находит данные для запрошенной операции.
     * @param refreshToken параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    Optional<Token> findByRefreshToken(String refreshToken);
}
