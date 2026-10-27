package com.healthcare.profile.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.healthcare.profile.dto.PatientPatchRequest;
import com.healthcare.profile.dto.PatientRequest;
import com.healthcare.profile.dto.PatientResponse;
import com.healthcare.profile.dto.StatusUpdateRequest;


public interface PatientProfileService {

    String createNewPatient(PatientRequest request);

    PatientResponse getPatientById(Long id);

    Page<PatientResponse> getAllPatients(
            int page,
            int size,
            String sortBy,
            String direction);

    PatientResponse updatePatient(
            Long id,
            PatientRequest request);

    PatientResponse updatePatientPartially(
            Long id,
            PatientPatchRequest request);

    PatientResponse updatePatientStatus(
            Long id,
            StatusUpdateRequest request);

    List<PatientResponse> searchPatients(
            String firstName,
            String lastName,
            String phoneNumber,
            String email);

    String deletePatient(Long id);
}
