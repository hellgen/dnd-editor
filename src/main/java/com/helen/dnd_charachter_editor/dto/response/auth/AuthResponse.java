package com.helen.dnd_charachter_editor.dto.response.auth;



/**
 * Data transfer object for auth response.
 */
public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {}
