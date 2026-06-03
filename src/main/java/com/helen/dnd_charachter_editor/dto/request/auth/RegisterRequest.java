package com.helen.dnd_charachter_editor.dto.request.auth;

/**
 * Объект передачи данных `RegisterRequest`.
 */
public record RegisterRequest (
        String email,
        String username,
        String password
) {}
