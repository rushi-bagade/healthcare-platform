package com.healthcare.encounter.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        return build(
                HttpStatus.BAD_REQUEST,
                "Validation failed: " + fieldErrors,
                request.getRequestURI());
    }

    @ExceptionHandler(EncounterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEncounterNotFound(
            EncounterNotFoundException ex,
            HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentNotFound(
            AppointmentNotFoundException ex,
            HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(DuplicateEncounterException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DuplicateEncounterException ex,
            HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(InvalidEncounterStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidState(
            InvalidEncounterStateException ex,
            HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponse> handleExternalService(
            ExternalServiceException ex,
            HttpServletRequest request) {
        return build(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {
        return build(
                HttpStatus.CONFLICT,
                "Database constraint violation",
                request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error occurred",
                request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> build(
            HttpStatus status,
            String message,
            String path) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path);
        return ResponseEntity.status(status).body(response);
    }
}
