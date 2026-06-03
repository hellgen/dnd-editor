package com.helen.dnd_charachter_editor.service.auth;

import com.helen.dnd_charachter_editor.entity.auth.Token;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.repository.auth.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.function.Function;


/**
 * Реализация сервиса `JwtService`.
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.secret_key}")
    private String secretKey;

    @Value("${security.jwt.access_token_expiration}")
    private Duration accessTokenExpiration;

    @Value("${security.jwt.refresh_token_expiration}")
    private Duration refreshTokenExpiration;

    private final TokenRepository tokenRepository;

    /**
     * Проверяет состояние для запрошенной операции.
     * @param token параметр, используемый при выполнении операции
     * @param user параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        boolean isActiveToken = tokenRepository.findByAccessToken(token)
                .map(t -> !t.getLoggedOut()).orElse(false);

        return username.equals(user.getUsername())
                && !isTokenExpired(token)
                && isActiveToken;
    }

    /**
     * Проверяет состояние для запрошенной операции.
     * @param token параметр, используемый при выполнении операции
     * @param user параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public boolean isValidRefresh(String token, User user) {
        String username = extractUsername(token);

        boolean isActiveRefreshToken = tokenRepository.findByRefreshToken(token)
                .map(t -> !t.getLoggedOut()).orElse(false);

        return username.equals(user.getUsername())
                && !isTokenExpired(token)
                && isActiveRefreshToken;
    }

    /**
     * Проверяет состояние для запрошенной операции.
     * @param token параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлекает данные для запрошенной операции.
     * @param token параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлекает данные для запрошенной операции.
     * @param token параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлекает данные для запрошенной операции.
     * @param token параметр, используемый при выполнении операции
     * @param resolver параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    /**
     * Извлекает данные для запрошенной операции.
     * @param token параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Генерирует данные для запрошенной операции.
     * @param user параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpiration);
    }

    /**
     * Генерирует данные для запрошенной операции.
     * @param user параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpiration);
    }

    /**
     * Генерирует данные для запрошенной операции.
     * @param user параметр, используемый при выполнении операции
     * @param expiryTime параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    private String generateToken(User user, Duration expiryTime) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiryTime.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Делает данные недействительными для запрошенной операции.
     * @param refreshToken параметр, используемый при выполнении операции
     */
    public void invalidateRefreshToken(String refreshToken) {
        tokenRepository.findByRefreshToken(refreshToken).ifPresent(token -> {
            token.setLoggedOut(true);
            tokenRepository.save(token);
        });
    }

    /**
     * Проверяет корректность данных для запрошенной операции.
     * @param refreshToken параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public User validateRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken)
                .filter(t -> !t.getLoggedOut())
                .map(Token::getUser)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        return (User) auth.getPrincipal();
    }

}
