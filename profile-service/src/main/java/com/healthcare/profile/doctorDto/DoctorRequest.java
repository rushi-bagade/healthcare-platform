package com.healthcare.profile.doctorDto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data 
public class DoctorRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @NotBlank
    private String gender;

    @NotBlank
    private String specialization;

    @NotBlank
    private String qualification;

    @NotBlank
    private String licenseNumber;

    @NotNull
    @Min(0)
    private Integer experienceYears;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;

    @Email
    private String email;

    private String address;

  
}