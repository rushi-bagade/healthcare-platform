package com.healthcare.appointment.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.healthcare.appointment.entity.AppointmentStatus;

import lombok.Data;

@Data
public class AppointmentResponse {

    private Long id;

    private String appointmentNumber;

    private Long patientId;

    private Long doctorId;

    private LocalDate appointmentDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String reason;

    private AppointmentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}