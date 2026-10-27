package com.healthcare.profile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PatientPatchRequest {

    private String firstName;

    private String lastName;

    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;

    @Email
    private String email;

    private String gender;

    private String address;

    private String bloodGroup;
}
