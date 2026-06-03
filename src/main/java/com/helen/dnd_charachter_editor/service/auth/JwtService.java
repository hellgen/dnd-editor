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
 * Default service implementation for jwt service operations.
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
     * Executes the is valid operation.
     * @param token value used by this operation
     * @param user value used by this operation
     * @return result of the operation
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
     * Executes the is valid refresh operation.
     * @param token value used by this operation
     * @param user value used by this operation
     * @return result of the operation
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
     * Executes the is token expired operation.
     * @param token value used by this operation
     * @return result of the operation
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts expiration.
     * @param token value used by this operation
     * @return result of the operation
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts username.
     * @param token value used by this operation
     * @return result of the operation
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts claim.
     * @param token value used by this operation
     * @param resolver value used by this operation
     * @return result of the operation
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    /**
     * Extracts all claims.
     * @param token value used by this operation
     * @return result of the operation
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
     * Generates access token.
     * @param user value used by this operation
     * @return result of the operation
     */
    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpiration);
    }

    /**
     * Generates refresh token.
     * @param user value used by this operation
     * @return result of the operation
     */
    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpiration);
    }

    /**
     * Generates token.
     * @param user value used by this operation
     * @param expiryTime value used by this operation
     * @return result of the operation
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
     * Returns signing key.
     * @return result of the operation
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Invalidates refresh token.
     * @param refreshToken value used by this operation
     */
    public void invalidateRefreshToken(String refreshToken) {
        tokenRepository.findByRefreshToken(refreshToken).ifPresent(token -> {
            token.setLoggedOut(true);
            tokenRepository.save(token);
        });
    }

    /**
     * Validates refresh token.
     * @param refreshToken value used by this operation
     * @return result of the operation
     */
    public User validateRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken)
                .filter(t -> !t.getLoggedOut())
                .map(Token::getUser)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }

    /**
     * Returns current user.
     * @return result of the operation
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        return (User) auth.getPrincipal();
    }

}
