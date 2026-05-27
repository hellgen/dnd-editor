package com.helen.dnd_charachter_editor.service.auth;

import com.helen.dnd_charachter_editor.dto.request.auth.LoginRequest;
import com.helen.dnd_charachter_editor.dto.request.auth.RefreshTokenRequest;
import com.helen.dnd_charachter_editor.dto.request.auth.RegisterRequest;
import com.helen.dnd_charachter_editor.dto.response.auth.AuthResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    void logout(String refreshToken);

    User getCurrentUser();

    AuthResponse refresh(RefreshTokenRequest request);
}
