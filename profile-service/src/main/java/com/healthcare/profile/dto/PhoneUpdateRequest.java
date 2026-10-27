package com.healthcare.profile.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PhoneUpdateRequest {

    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;

    // getter/setter
}
