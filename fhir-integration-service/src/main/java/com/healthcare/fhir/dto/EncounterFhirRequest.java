package com.healthcare.fhir.dto;

import java.time.LocalDate;

public record EncounterFhirRequest(
        Long encounterId,
        Long appointmentId,
        Long patientId,
        Long doctorId,
        LocalDate encounterDate,
        String status,
        String diagnosis,
        String clinicalNotes) {
}