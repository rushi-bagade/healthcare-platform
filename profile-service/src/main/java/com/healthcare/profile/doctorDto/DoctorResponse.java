package com.healthcare.profile.doctorDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data 
public class DoctorResponse {

    private Long id;

    private String doctorNumber;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String specialization;

    private String qualification;

    private String licenseNumber;

    private Integer experienceYears;

    private String phoneNumber;

    private String email;

    private String address;

    private DoctorStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

   
}