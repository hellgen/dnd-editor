package com.helen.dnd_charachter_editor.service.auth.impl;

import com.helen.dnd_charachter_editor.dto.request.auth.LoginRequest;
import com.helen.dnd_charachter_editor.dto.request.auth.RefreshTokenRequest;
import com.helen.dnd_charachter_editor.dto.request.auth.RegisterRequest;
import com.helen.dnd_charachter_editor.dto.response.auth.AuthResponse;
import com.helen.dnd_charachter_editor.entity.auth.Token;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.mapper.auth.UserMapper;
import com.helen.dnd_charachter_editor.repository.auth.TokenRepository;
import com.helen.dnd_charachter_editor.repository.auth.UserRepository;
import com.helen.dnd_charachter_editor.service.auth.AuthService;
import com.helen.dnd_charachter_editor.service.auth.JwtService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = UserMapper.getUserFromDto(request);

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Хэшируем пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Instant now = Instant.now();

        user.setCreatedAt(now);

        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Token token = createToken(accessToken, refreshToken, user);

        tokenRepository.save(token);

        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.email(),
//                        request.password()
//                )
//        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Token token = createToken(accessToken, refreshToken, user);

        tokenRepository.save(token);

        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }

    @Override
    public void logout(String refreshToken) {
        // При твоём подходе — просто инвалидируем токен, не трогая базу
        jwtService.invalidateRefreshToken(refreshToken);
    }

    @Override
    public User getCurrentUser() {
        return jwtService.getCurrentUser();
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        User user = jwtService.validateRefreshToken(request.refreshToken());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Token token = createToken(accessToken, refreshToken, user);

        tokenRepository.save(token);

        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }

    private Token createToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();

        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);

        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
