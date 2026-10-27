package com.healthcare.profile.dto;

import java.time.LocalDate;

public record PatientCreatedEvent(
        Long patientId,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String gender,
        String phoneNumber,
        String email,
        String address,
        String bloodGroup
) {}