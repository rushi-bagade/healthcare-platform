package com.healthcare.appointment.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDoctorNotFound(
            DoctorNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse e = new ErrorResponse();

        e.setMessage(ex.getMessage());
        e.setTimestamp(LocalDateTime.now());
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        e.setPath(request.getRequestURI());

        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFound(
            PatientNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse e = new ErrorResponse();

        e.setMessage(ex.getMessage());
        e.setTimestamp(LocalDateTime.now());
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        e.setPath(request.getRequestURI());

        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AppointmentSlotNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleSlotNotAvailable(
        AppointmentSlotNotAvailableException ex,
        HttpServletRequest request) {

    ErrorResponse e = new ErrorResponse();

    e.setTimestamp(LocalDateTime.now());
    e.setStatus(HttpStatus.CONFLICT.value());
    e.setError(HttpStatus.CONFLICT.getReasonPhrase());
    e.setMessage(ex.getMessage());
    e.setPath(request.getRequestURI());

    return new ResponseEntity<>(
            e,
            HttpStatus.CONFLICT);
}

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentNotFound(
        AppointmentNotFoundException ex,
        HttpServletRequest request) {

    ErrorResponse e = new ErrorResponse();

    e.setTimestamp(LocalDateTime.now());
    e.setStatus(HttpStatus.NOT_FOUND.value());
    e.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
    e.setMessage(ex.getMessage());
    e.setPath(request.getRequestURI());

    return new ResponseEntity<>(
            e,
            HttpStatus.NOT_FOUND);
}
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(
        IllegalStateException ex,
        HttpServletRequest request) {

    ErrorResponse e = new ErrorResponse();

    e.setTimestamp(LocalDateTime.now());
    e.setStatus(HttpStatus.BAD_REQUEST.value());
    e.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
    e.setMessage(ex.getMessage());
    e.setPath(request.getRequestURI());

    return new ResponseEntity<>(
            e,
            HttpStatus.BAD_REQUEST);
}

}