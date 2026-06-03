package com.helen.dnd_charachter_editor.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * Компонент `CustomAccessDeniedHandler` для обработки исключительных ситуаций.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Обрабатывает запрошенную ситуацию.
     * @param request параметр, используемый при выполнении операции
     * @param response параметр, используемый при выполнении операции
     * @param accessDeniedException параметр, используемый при выполнении операции
     * @throws IOException если операцию невозможно выполнить
     * @throws ServletException если операцию невозможно выполнить
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setStatus(403);
    }
}
