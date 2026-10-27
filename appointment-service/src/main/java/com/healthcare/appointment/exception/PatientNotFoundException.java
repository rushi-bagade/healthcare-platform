package com.healthcare.appointment.exception;

public class PatientNotFoundException
        extends RuntimeException {

    public PatientNotFoundException(String message) {
        super(message);
    }
}