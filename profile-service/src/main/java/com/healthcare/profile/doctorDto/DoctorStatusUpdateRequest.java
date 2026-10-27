package com.healthcare.profile.doctorDto;

import jakarta.validation.constraints.NotNull;


public class DoctorStatusUpdateRequest {

    @NotNull
    private DoctorStatus status;

    public DoctorStatus getStatus() {
        return status;
    }

    public void setStatus(DoctorStatus status) {
        this.status = status;
    }
}
