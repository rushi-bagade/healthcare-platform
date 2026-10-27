package com.healthcare.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentRequest {

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    @NotNull
    @FutureOrPresent
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotBlank
    private String reason;

}
