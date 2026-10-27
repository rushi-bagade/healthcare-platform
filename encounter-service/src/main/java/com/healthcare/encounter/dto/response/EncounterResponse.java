package com.healthcare.encounter.dto.response;

import com.healthcare.encounter.enums.EncounterStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EncounterResponse {

    private Long id;
    private String encounterNumber;
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private LocalDate encounterDate;
    private String chiefComplaint;
    private String diagnosis;
    private String clinicalNotes;
    private String treatmentNotes;
    private EncounterStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
