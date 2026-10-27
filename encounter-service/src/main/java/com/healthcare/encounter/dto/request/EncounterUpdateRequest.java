package com.healthcare.encounter.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EncounterUpdateRequest {

    @NotBlank
    private String chiefComplaint;

    private String diagnosis;
    private String clinicalNotes;
    private String treatmentNotes;
}
