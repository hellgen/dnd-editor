package com.helen.dnd_charachter_editor.service;

import com.helen.dnd_charachter_editor.entity.Token;
import com.helen.dnd_charachter_editor.entity.User;
import com.helen.dnd_charachter_editor.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${security.jwt.secret_key}")
    private String secretKey;

    @Value("${security.jwt.access_token_expiration}")
    private long accessTokenExpiration;

    @Value("${security.jwt.refresh_token_expiration}")
    private long refreshTokenExpiration;

    private final TokenRepository tokenRepository;

    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        boolean isActiveToken = tokenRepository.findByAccessToken(token)
                .map(t -> !t.getLoggedOut()).orElse(false);

        return username.equals(user.getUsername())
                && !isTokenExpired(token)
                && isActiveToken;
    }

    public boolean isValidRefresh(String token, User user) {
        String username = extractUsername(token);

        boolean isActiveRefreshToken = tokenRepository.findByRefreshToken(token)
                .map(t -> !t.getLoggedOut()).orElse(false);

        return username.equals(user.getUsername())
                && !isTokenExpired(token)
                && isActiveRefreshToken;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpiration);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpiration);
    }

    private String generateToken(User user, long expiryTime) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiryTime))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void invalidateRefreshToken(String refreshToken) {
        tokenRepository.findByRefreshToken(refreshToken).ifPresent(token -> {
            token.setLoggedOut(true);
            tokenRepository.save(token);
        });
    }

    public User validateRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken)
                .filter(t -> !t.getLoggedOut())
                .map(Token::getUser)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        return (User) auth.getPrincipal();
    }

}
