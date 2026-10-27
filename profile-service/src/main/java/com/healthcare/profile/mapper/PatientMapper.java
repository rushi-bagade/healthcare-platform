package com.healthcare.profile.mapper;

import  com.healthcare.profile.entity.Patient;

import org.springframework.stereotype.Component;

import com.healthcare.profile.dto.PatientRequest;
import com.healthcare.profile.dto.PatientResponse;

@Component 
public class PatientMapper {

       public Patient toEntity(PatientRequest request) {

        Patient patient = new Patient();

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setBloodGroup(request.getBloodGroup());

        return patient;
    }

    public PatientResponse toResponse(Patient patient) {

        PatientResponse response = new PatientResponse();

        response.setId(patient.getId());
        response.setPatientNumber(patient.getPatientNumber());
        response.setFirstName(patient.getFirstName());
        response.setLastName(patient.getLastName());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setGender(patient.getGender());
        response.setPhoneNumber(patient.getPhoneNumber());
        response.setEmail(patient.getEmail());
        response.setAddress(patient.getAddress());
        response.setBloodGroup(patient.getBloodGroup());
        response.setStatus(patient.getStatus());
        response.setCreatedAt(patient.getCreatedAt());
        response.setUpdatedAt(patient.getUpdatedAt());

        return response;
    }

    public void updateEntity(
            PatientRequest request,
            Patient patient) {

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setBloodGroup(request.getBloodGroup());
    }
}
