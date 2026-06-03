package com.helen.dnd_charachter_editor.dto.response.auth;



/**
 * Объект передачи данных `AuthResponse`.
 */
public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {}
