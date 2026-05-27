package com.helen.dnd_charachter_editor.dto.request.auth;

public record RegisterRequest (
        String email,
        String username,
        String password
) {}
