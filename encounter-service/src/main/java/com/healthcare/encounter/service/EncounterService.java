package com.healthcare.encounter.service;

import com.healthcare.encounter.dto.request.EncounterRequest;
import com.healthcare.encounter.dto.request.EncounterStatusUpdateRequest;
import com.healthcare.encounter.dto.request.EncounterUpdateRequest;
import com.healthcare.encounter.dto.response.EncounterResponse;
import org.springframework.data.domain.Page;

public interface EncounterService {

    EncounterResponse createEncounter(EncounterRequest request);

    EncounterResponse getEncounterById(Long id);

    Page<EncounterResponse> getAllEncounters(
            int page,
            int size,
            String sortBy,
            String direction);

    Page<EncounterResponse> getEncountersByPatient(
            Long patientId,
            int page,
            int size,
            String sortBy,
            String direction);

    Page<EncounterResponse> getEncountersByDoctor(
            Long doctorId,
            int page,
            int size,
            String sortBy,
            String direction);

    EncounterResponse getEncounterByAppointment(Long appointmentId);

    EncounterResponse updateEncounter(Long id, EncounterUpdateRequest request);

    EncounterResponse updateEncounterStatus(
            Long id,
            EncounterStatusUpdateRequest request);
}
