package com.helen.dnd_charachter_editor.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Компонент `GlobalExceptionHandler` для обработки исключительных ситуаций.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает запрошенную ситуацию.
     * @param exception параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(
            EntityNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    /**
     * Обрабатывает запрошенную ситуацию.
     * @param exception параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(
            IllegalArgumentException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
