package com.healthcare.profile.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.healthcare.profile.doctorDto.DoctorStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data 
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_number", unique = true, nullable = false)
    private String doctorNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String qualification;

    @Column(name = "license_number", unique = true, nullable = false)
    private String licenseNumber;

    @Column(name = "experience_years", nullable = false)
    private Integer experienceYears;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DoctorStatus status = DoctorStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}