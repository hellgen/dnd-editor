package com.helen.dnd_charachter_editor.dto.request.registration;

public record LoginRequest (
        String email,
        String password
) {}