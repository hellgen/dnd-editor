package com.helen.dnd_charachter_editor.service.auth;

import com.helen.dnd_charachter_editor.dto.request.auth.LoginRequest;
import com.helen.dnd_charachter_editor.dto.request.auth.RefreshTokenRequest;
import com.helen.dnd_charachter_editor.dto.request.auth.RegisterRequest;
import com.helen.dnd_charachter_editor.dto.response.auth.AuthResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Service contract for auth service operations.
 */
public interface AuthService extends UserDetailsService {

    /**
     * Registers the requested operation.
     * @param request value used by this operation
     * @return result of the operation
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Authenticates the requested operation.
     * @param request value used by this operation
     * @return result of the operation
     */
    AuthResponse login(LoginRequest request);

    /**
     * Logs out the requested operation.
     * @param refreshToken value used by this operation
     */
    void logout(String refreshToken);

    /**
     * Returns current user.
     * @return result of the operation
     */
    User getCurrentUser();

    /**
     * Refreshes the requested operation.
     * @param request value used by this operation
     * @return result of the operation
     */
    AuthResponse refresh(RefreshTokenRequest request);
}
