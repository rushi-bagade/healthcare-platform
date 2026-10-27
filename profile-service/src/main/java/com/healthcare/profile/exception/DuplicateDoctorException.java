package com.healthcare.profile.exception;


public class DuplicateDoctorException extends RuntimeException {

    public DuplicateDoctorException(String message) {
        super(message);
    }
}