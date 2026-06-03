package com.helen.dnd_charachter_editor.mapper.auth;

import com.helen.dnd_charachter_editor.dto.request.auth.RegisterRequest;
import com.helen.dnd_charachter_editor.entity.auth.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Маппер `UserMapper` для преобразования данных между слоями приложения.
 */
public class UserMapper {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Возвращает данные для запрошенной операции.
     * @param request параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static User getUserFromDto(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        return user;
    }
}
