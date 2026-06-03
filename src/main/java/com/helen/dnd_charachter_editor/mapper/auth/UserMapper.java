package com.helen.dnd_charachter_editor.mapper.auth;

import com.helen.dnd_charachter_editor.dto.request.auth.RegisterRequest;
import com.helen.dnd_charachter_editor.entity.auth.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Mapper that converts user mapper values between layers.
 */
public class UserMapper {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Returns user from dto.
     * @param request value used by this operation
     * @return result of the operation
     */
    public static User getUserFromDto(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        return user;
    }
}
