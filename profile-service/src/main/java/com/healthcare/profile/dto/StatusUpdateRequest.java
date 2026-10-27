package com.healthcare.profile.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest {

    @NotNull
    private PatientStatus status;
}
