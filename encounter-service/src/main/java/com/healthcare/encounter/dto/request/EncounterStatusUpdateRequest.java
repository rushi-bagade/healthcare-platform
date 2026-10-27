package com.healthcare.encounter.dto.request;

import com.healthcare.encounter.enums.EncounterStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EncounterStatusUpdateRequest {

    @NotNull
    private EncounterStatus status;
}
