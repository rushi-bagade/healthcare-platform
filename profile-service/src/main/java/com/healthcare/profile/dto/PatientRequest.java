package com.healthcare.profile.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PatientRequest {

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
    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;

    @Email
    private String email;

    private String address;

    @NotBlank
    private String bloodGroup;

}
