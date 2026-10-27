package com.healthcare.appointment.exception;

public class AppointmentSlotNotAvailableException
        extends RuntimeException {

    public AppointmentSlotNotAvailableException(String message) {
        super(message);
    }
}