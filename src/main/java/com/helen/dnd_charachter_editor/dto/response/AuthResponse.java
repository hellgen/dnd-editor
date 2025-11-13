package com.helen.dnd_charachter_editor.dto.response;



public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {}
