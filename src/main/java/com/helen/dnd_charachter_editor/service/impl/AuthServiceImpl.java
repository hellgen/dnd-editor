package com.helen.dnd_charachter_editor.service.impl;

import com.helen.dnd_charachter_editor.dto.request.LoginRequest;
import com.helen.dnd_charachter_editor.dto.request.RefreshTokenRequest;
import com.helen.dnd_charachter_editor.dto.request.RegisterRequest;
import com.helen.dnd_charachter_editor.dto.response.AuthResponse;
import com.helen.dnd_charachter_editor.entity.User;
import com.helen.dnd_charachter_editor.mapper.UserMapper;
import com.helen.dnd_charachter_editor.repository.UserRepository;
import com.helen.dnd_charachter_editor.service.AuthService;
import com.helen.dnd_charachter_editor.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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

        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

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

        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }
}
