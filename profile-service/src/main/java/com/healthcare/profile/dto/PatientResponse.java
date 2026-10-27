package com.healthcare.profile.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data 
public class PatientResponse {

     private Long id;

    private String patientNumber;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String phoneNumber;

    private String email;

    private String address;

    private String bloodGroup;

    private PatientStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}