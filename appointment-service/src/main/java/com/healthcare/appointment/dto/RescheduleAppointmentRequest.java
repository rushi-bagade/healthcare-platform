package com.healthcare.appointment.dto;


import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data 
public class RescheduleAppointmentRequest {

    @NotNull
    @FutureOrPresent
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

}
