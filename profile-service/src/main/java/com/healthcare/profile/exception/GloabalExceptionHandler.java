package com.healthcare.profile.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GloabalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlErrorResponse(MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ErrorResponse e = new ErrorResponse();

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        e.setMessage(message);
        e.setTimestamp(LocalDateTime.now());
        e.setStatus(HttpStatus.BAD_REQUEST.value());
        e.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        e.setPath(request.getRequestURI());

        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatePatientException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePatientException(DuplicatePatientException ex,
            HttpServletRequest request) {

        ErrorResponse er = new ErrorResponse();
        er.setTimestamp(LocalDateTime.now());
        er.setStatus(404);
        er.setError("Patient Alredy Exists");
        er.setMessage(ex.getMessage());
        er.setPath(request.getRequestURI());

        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> HandlePatientNotFoundException(PatientNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse e = new ErrorResponse();
        e.setMessage(ex.getMessage());
        e.setTimestamp(LocalDateTime.now());
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        e.setPath(request.getRequestURI());

        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDoctorNotFound(
            DoctorNotFoundException ex, HttpServletRequest request) {

        ErrorResponse e = new ErrorResponse();
        e.setMessage(ex.getMessage());
        e.setTimestamp(LocalDateTime.now());
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        e.setPath(request.getRequestURI());

        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateDoctorException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateDoctor(
            DuplicateDoctorException ex, HttpServletRequest request) {

        ErrorResponse er = new ErrorResponse();
        er.setTimestamp(LocalDateTime.now());
        er.setStatus(404);
        er.setError("Doctor Alredy Exists");
        er.setMessage(ex.getMessage());
        er.setPath(request.getRequestURI());

        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
    }

}
