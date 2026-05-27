package com.helen.dnd_charachter_editor.dto.request.auth;

public record LoginRequest (
        String email,
        String password
) {}