package com.healthcare.encounter.exception;

public class InvalidEncounterStateException extends RuntimeException {

    public InvalidEncounterStateException(String message) {
        super(message);
    }
}
