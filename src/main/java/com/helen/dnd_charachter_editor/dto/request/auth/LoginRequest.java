package com.helen.dnd_charachter_editor.dto.request.auth;

/**
 * Объект передачи данных `LoginRequest`.
 */
public record LoginRequest (
        String email,
        String password
) {}
