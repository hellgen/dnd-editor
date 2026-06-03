package com.helen.dnd_charachter_editor.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * Component that handles custom access denied handler concerns.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles the requested operation.
     * @param request value used by this operation
     * @param response value used by this operation
     * @param accessDeniedException value used by this operation
     * @throws IOException when the operation cannot be completed
     * @throws ServletException when the operation cannot be completed
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setStatus(403);
    }
}
