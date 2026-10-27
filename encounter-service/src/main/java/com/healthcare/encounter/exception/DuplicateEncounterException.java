package com.healthcare.encounter.exception;

public class DuplicateEncounterException extends RuntimeException {

    public DuplicateEncounterException(String message) {
        super(message);
    }
}
