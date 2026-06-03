package com.helen.dnd_charachter_editor.dto.request.auth;

/**
 * Data transfer object for register request.
 */
public record RegisterRequest (
        String email,
        String username,
        String password
) {}
