package com.healthcare.profile.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.healthcare.profile.dto.PatientCreatedEvent;
import com.healthcare.profile.client.FhirIntegrationClient;
import com.healthcare.profile.dto.PatientFhirRequest;
import com.healthcare.profile.dto.PatientPatchRequest;
import com.healthcare.profile.dto.PatientRequest;
import com.healthcare.profile.dto.PatientResponse;
import com.healthcare.profile.dto.PatientStatus;
import com.healthcare.profile.dto.StatusUpdateRequest;
import com.healthcare.profile.entity.Patient;
import com.healthcare.profile.exception.DuplicatePatientException;
import com.healthcare.profile.exception.PatientNotFoundException;
import com.healthcare.profile.mapper.PatientMapper;
import com.healthcare.profile.repository.PatientRepository;
import com.healthcare.profile.publisher.PatientEventPublisher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

@Service
public class PatientServiceImpl implements PatientProfileService {

    private final PatientRepository patientRepository;
    private final PatientMapper mapper;
    private final FhirIntegrationClient fhirIntegrationClient;
    private final PatientEventPublisher patientEventPublisher;

    public PatientServiceImpl(
            PatientRepository patientRepository,
            PatientMapper mapper,
            FhirIntegrationClient fhirIntegrationClient,
            PatientEventPublisher patientEventPublisher) {

        this.patientRepository = patientRepository;
        this.mapper = mapper;
        this.fhirIntegrationClient = fhirIntegrationClient;
        this.patientEventPublisher= patientEventPublisher;
    }

    @Override
    public String createNewPatient(PatientRequest request) {

        if (patientRepository.existsByPhoneNumber(
                request.getPhoneNumber())) {

            throw new DuplicatePatientException(
                    "Patient with phone number "
                            + request.getPhoneNumber()
                            + " already exists");
        }

        if (request.getEmail() != null
                && patientRepository.existsByEmail(request.getEmail())) {

            throw new DuplicatePatientException(
                    "Patient with email "
                            + request.getEmail()
                            + " already exists");
        }

        Patient patient = mapper.toEntity(request);

        patient.setPatientNumber(generatePatientNumber());
        patient.setStatus(PatientStatus.ACTIVE);

        Patient savedPatient = patientRepository.save(patient);

        PatientCreatedEvent event = new PatientCreatedEvent(
       savedPatient.getId(),
        savedPatient.getFirstName(),
        savedPatient.getLastName(),
        savedPatient.getDateOfBirth(),
        savedPatient.getGender().toString(),
        savedPatient.getPhoneNumber(),
        savedPatient.getEmail(),
        savedPatient.getAddress(),
        savedPatient.getBloodGroup()
        );

        patientEventPublisher.publishPatientCreated(event);

        // PatientFhirRequest fhirRequest = new PatientFhirRequest(
        //         savedPatient.getId(),
        //         savedPatient.getFirstName(),
        //         savedPatient.getLastName(),
        //         savedPatient.getDateOfBirth(),
        //         savedPatient.getGender(),
        //         savedPatient.getPhoneNumber(),
        //         savedPatient.getEmail(),
        //          savedPatient.getAddress(),
        //          savedPatient.getBloodGroup());

        // fhirIntegrationClient.createPatient(fhirRequest);

        return "Patient Created Successfully!";
    }

    private String generatePatientNumber() {
        return "P" + System.currentTimeMillis();
    }

    private Patient getPatient(Long id) {

        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient not found with id: " + id));
    }

    @Override
    public PatientResponse getPatientById(Long id) {

        Patient patient = getPatient(id);

        return mapper.toResponse(patient);
    }

    @Override
    public Page<PatientResponse> getAllPatients(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort;

        if ("desc".equalsIgnoreCase(direction)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Patient> patients = patientRepository.findByStatus(
                PatientStatus.ACTIVE,
                pageable);

        return patients.map(mapper::toResponse);
    }

    @Override
    public PatientResponse updatePatient(
            Long id,
            PatientRequest request) {

        Patient patient = getPatient(id);

        mapper.updateEntity(request, patient);

        Patient updatedPatient = patientRepository.save(patient);

        return mapper.toResponse(updatedPatient);
    }

    @Override
    public PatientResponse updatePatientPartially(
            Long id,
            PatientPatchRequest request) {

        Patient patient = getPatient(id);

        if (request.getFirstName() != null) {
            patient.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            patient.setLastName(request.getLastName());
        }

        if (request.getPhoneNumber() != null) {
            patient.setPhoneNumber(
                    request.getPhoneNumber());
        }

        if (request.getEmail() != null) {
            patient.setEmail(request.getEmail());
        }

        if (request.getGender() != null) {
            patient.setGender(request.getGender());
        }

        if (request.getAddress() != null) {
            patient.setAddress(request.getAddress());
        }

        if (request.getBloodGroup() != null) {
            patient.setBloodGroup(
                    request.getBloodGroup());
        }

        Patient updatedPatient = patientRepository.save(patient);

        return mapper.toResponse(updatedPatient);
    }

    @Override
    public PatientResponse updatePatientStatus(
            Long id,
            StatusUpdateRequest request) {

        Patient patient = getPatient(id);

        patient.setStatus(request.getStatus());

        Patient updatedPatient = patientRepository.save(patient);

        return mapper.toResponse(updatedPatient);
    }

    @Override
    public List<PatientResponse> searchPatients(
            String firstName,
            String lastName,
            String phoneNumber,
            String email) {

        List<Patient> patients;

        if (firstName != null && !firstName.isBlank()) {

            patients = patientRepository
                    .findByFirstNameContainingIgnoreCase(
                            firstName);

        } else if (lastName != null && !lastName.isBlank()) {

            patients = patientRepository
                    .findByLastNameContainingIgnoreCase(
                            lastName);

        } else if (phoneNumber != null
                && !phoneNumber.isBlank()) {

            patients = patientRepository
                    .findByPhoneNumber(phoneNumber);

        } else if (email != null && !email.isBlank()) {

            patients = patientRepository
                    .findByEmail(email);

        } else {
            return List.of();
        }

        return patients.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public String deletePatient(Long id) {

        Patient patient = getPatient(id);

        patient.setStatus(PatientStatus.INACTIVE);

        patientRepository.save(patient);

        return "Patient deleted successfully";
    }

}
