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
 * REST controller that exposes auth controller endpoints.
 */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * Registers the requested operation.
     * @param request value used by this operation
     * @return result of the operation
     */
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    /**
     * Authenticates the requested operation.
     * @param request value used by this operation
     * @return result of the operation
     */
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Executes the logiut operation.
     * @param refreshToken value used by this operation
     */
    @PostMapping("/logout/{refreshToken}")
    public void logiut(@PathVariable String refreshToken) {
        authService.logout(refreshToken);
    }

    /**
     * Refreshes token.
     * @param request value used by this operation
     * @return result of the operation
     */
    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refresh(request);
    }
}
