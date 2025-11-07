package com.helen.dnd_charachter_editor.dto.request;

public record RegisterRequest (
        String email,
        String username,
        String password
) {}
