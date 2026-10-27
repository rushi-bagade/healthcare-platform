package com.healthcare.fhir.dto;

import java.time.LocalDate;

public record PatientFhirRequest(
     Long patientId,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String gender,
        String phoneNumber,
        String email,
        String address,
        String bloodGroup
) {
}

