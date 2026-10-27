package com.healthcare.encounter.entity;

import com.healthcare.encounter.enums.EncounterStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Table(
        name = "encounters",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_encounter_appointment",
                        columnNames = "appointment_id")
        })
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "encounter_number", unique = true, nullable = false, length = 30)
    private String encounterNumber;

    @Column(name = "appointment_id", nullable = false)
    private Long appointmentId;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "encounter_date", nullable = false)
    private LocalDate encounterDate;

    @Column(name = "chief_complaint", nullable = false, length = 500)
    private String chiefComplaint;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "clinical_notes", columnDefinition = "TEXT")
    private String clinicalNotes;

    @Column(name = "treatment_notes", columnDefinition = "TEXT")
    private String treatmentNotes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EncounterStatus status = EncounterStatus.OPEN;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
