package com.healthcare.encounter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EncounterRequest {

    @NotNull
    private Long appointmentId;

    @NotBlank
    private String chiefComplaint;

    private String diagnosis;
    private String clinicalNotes;
    private String treatmentNotes;
}
