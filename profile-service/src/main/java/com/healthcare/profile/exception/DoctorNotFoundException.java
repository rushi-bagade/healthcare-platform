package com.healthcare.profile.exception;


public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(String message) {
        super(message);
    }
}