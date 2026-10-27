package com.healthcare.fhir.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentFhirRequest(
        Long appointmentId,
        String appointmentNumber,
        Long patientId,
        Long doctorId,
        LocalDate appointmentDate,
        LocalTime startTime,
        LocalTime endTime,
        String reason,
        String status) {
}