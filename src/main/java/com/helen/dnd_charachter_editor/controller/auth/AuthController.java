package com.helen.dnd_charachter_editor.controller.auth;

import com.helen.dnd_charachter_editor.dto.request.auth.LoginRequest;
import com.helen.dnd_charachter_editor.dto.request.auth.RefreshTokenRequest;
import com.helen.dnd_charachter_editor.dto.request.auth.RegisterRequest;
import com.helen.dnd_charachter_editor.dto.response.auth.AuthResponse;
import com.helen.dnd_charachter_editor.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер REST API для обработки запросов `AuthController`.
 */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * Регистрирует пользователя.
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    /**
     * Выполняет вход пользователя.
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Выполняет запрошенную операцию.
     * @param refreshToken параметр, используемый при выполнении операции
     */
    @PostMapping("/logout/{refreshToken}")
    public void logiut(@PathVariable String refreshToken) {
        authService.logout(refreshToken);
    }

    /**
     * Обновляет данные аутентификации.
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refresh(request);
    }
}
