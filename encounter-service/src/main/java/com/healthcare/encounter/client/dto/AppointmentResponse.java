package com.healthcare.encounter.client.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AppointmentResponse(
        Long id,
        String appointmentNumber,
        Long patientId,
        Long doctorId,
        LocalDate appointmentDate,
        LocalTime startTime,
        LocalTime endTime,
        String reason,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
