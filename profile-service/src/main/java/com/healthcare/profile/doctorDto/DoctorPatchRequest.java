package com.healthcare.profile.doctorDto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data 
public class DoctorPatchRequest {

    private String firstName;

    private String lastName;

    @Past
    private LocalDate dateOfBirth;

    private String gender;

    private String specialization;

    private String qualification;

    private String licenseNumber;

    @Min(0)
    private Integer experienceYears;

    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;

    @Email
    private String email;

    private String address;

    }