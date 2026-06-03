package com.helen.dnd_charachter_editor.dto.request.auth;

/**
 * Data transfer object for refresh token request.
 */
public record RefreshTokenRequest (
        String refreshToken
) {}
