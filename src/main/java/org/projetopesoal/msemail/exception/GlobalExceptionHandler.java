package org.projetopesoal.msemail.exception;

import jakarta.validation.ConstraintViolationException;
import org.projetopesoal.msemail.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFound(
            NoResourceFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error("Not Found")
                        .message("Endpoint não encontrado")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(
                ApiErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Validation Error")
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * Erros de violação de constraints
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {

        return ResponseEntity.badRequest().body(
                ApiErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Constraint Violation")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * Erros relacionados ao envio de email
     */
    @ExceptionHandler(MailException.class)
    public ResponseEntity<ApiErrorResponse> handleMailException(
            MailException ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiErrorResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Email Sending Error")
                        .message("Erro ao enviar email")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * Erro genérico (fallback)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiErrorResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Internal Server Error")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
