package com.healthcare.appointment.dto;

import com.healthcare.appointment.entity.AppointmentStatus;

import jakarta.validation.constraints.NotNull;

public class AppointmentStatusUpdateRequest {

    @NotNull
    private AppointmentStatus status;

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}