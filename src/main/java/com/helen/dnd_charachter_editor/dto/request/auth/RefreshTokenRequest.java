package com.helen.dnd_charachter_editor.dto.request.auth;

/**
 * Объект передачи данных `RefreshTokenRequest`.
 */
public record RefreshTokenRequest (
        String refreshToken
) {}
