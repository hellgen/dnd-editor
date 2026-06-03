package com.helen.dnd_charachter_editor.service.auth;

import com.helen.dnd_charachter_editor.dto.request.auth.LoginRequest;
import com.helen.dnd_charachter_editor.dto.request.auth.RefreshTokenRequest;
import com.helen.dnd_charachter_editor.dto.request.auth.RegisterRequest;
import com.helen.dnd_charachter_editor.dto.response.auth.AuthResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Контракт сервиса `AuthService`.
 */
public interface AuthService extends UserDetailsService {

    /**
     * Регистрирует пользователя.
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Выполняет вход пользователя.
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    AuthResponse login(LoginRequest request);

    /**
     * Выполняет выход пользователя.
     * @param refreshToken параметр, используемый при выполнении операции
     */
    void logout(String refreshToken);

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    User getCurrentUser();

    /**
     * Обновляет данные аутентификации.
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    AuthResponse refresh(RefreshTokenRequest request);
}
