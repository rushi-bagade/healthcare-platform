package com.healthcare.encounter.exception;

public class EncounterNotFoundException extends RuntimeException {

    public EncounterNotFoundException(String message) {
        super(message);
    }
}
