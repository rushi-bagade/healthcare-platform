package com.healthcare.profile.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.healthcare.profile.dto.PatientStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Data 
@Entity 
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_number", unique = true, nullable = false)
    private String patientNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String address;

    @Column(nullable = false)
    private String bloodGroup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PatientStatus status = PatientStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

