package com.helen.dnd_charachter_editor.dto.response.auth;



public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {}
