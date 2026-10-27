package com.healthcare.encounter.dto.request;

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