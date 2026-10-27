package com.healthcare.profile.doctorDto;

public record PractitionerFhirRequest(
        Long doctorId,
        String firstName,
        String lastName,
        String gender,
        String phoneNumber,
        String email,
        String specialization) {
}