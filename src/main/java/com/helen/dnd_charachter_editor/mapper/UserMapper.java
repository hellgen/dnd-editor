package com.helen.dnd_charachter_editor.mapper;

import com.helen.dnd_charachter_editor.dto.request.RegisterRequest;
import com.helen.dnd_charachter_editor.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User getUserFromDto(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        return user;
    }
}
