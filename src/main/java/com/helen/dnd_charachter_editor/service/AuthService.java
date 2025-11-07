package com.helen.dnd_charachter_editor.service;

import com.helen.dnd_charachter_editor.dto.request.LoginRequest;
import com.helen.dnd_charachter_editor.dto.request.RefreshTokenRequest;
import com.helen.dnd_charachter_editor.dto.request.RegisterRequest;
import com.helen.dnd_charachter_editor.dto.response.AuthResponse;
import com.helen.dnd_charachter_editor.entity.User;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    void logout(String refreshToken);

    User getCurrentUser() ;
    AuthResponse refresh(RefreshTokenRequest request);
}
