package com.helen.dnd_charachter_editor.dto.request.auth;

/**
 * Data transfer object for login request.
 */
public record LoginRequest (
        String email,
        String password
) {}
