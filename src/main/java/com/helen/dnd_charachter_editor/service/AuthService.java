package com.helen.dnd_charachter_editor.service;

import com.helen.dnd_charachter_editor.dto.request.registration.LoginRequest;
import com.helen.dnd_charachter_editor.dto.request.registration.RefreshTokenRequest;
import com.helen.dnd_charachter_editor.entity.RegisterRequest;
import com.helen.dnd_charachter_editor.dto.response.AuthResponse;
import com.helen.dnd_charachter_editor.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    void logout(String refreshToken);

    User getCurrentUser() ;
    AuthResponse refresh(RefreshTokenRequest request);
}
