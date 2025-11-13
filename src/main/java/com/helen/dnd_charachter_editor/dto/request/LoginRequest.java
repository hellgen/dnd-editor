package com.helen.dnd_charachter_editor.dto.request;

public record LoginRequest (
        String email,
        String password
) {}