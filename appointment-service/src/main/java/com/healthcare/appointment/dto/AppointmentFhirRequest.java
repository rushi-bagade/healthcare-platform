package com.healthcare.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentFhirRequest(
        Long appointmentId,
        Long patientId,
        Long doctorId,
        LocalDate appointmentDate,
        LocalTime startTime,
        LocalTime endTime,
        String status,
        String reason
) {}
