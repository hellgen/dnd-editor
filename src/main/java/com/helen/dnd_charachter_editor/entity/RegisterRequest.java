package com.helen.dnd_charachter_editor.entity;

public record RegisterRequest (
        String email,
        String username,
        String password
) {}
