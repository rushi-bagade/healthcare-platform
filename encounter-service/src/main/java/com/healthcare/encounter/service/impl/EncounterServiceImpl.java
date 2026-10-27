package com.healthcare.encounter.service.impl;

import com.healthcare.encounter.client.AppointmentServiceClient;
import com.healthcare.encounter.client.FhirIntegrationClient;
import com.healthcare.encounter.client.dto.AppointmentResponse;
import com.healthcare.encounter.dto.request.EncounterFhirRequest;
import com.healthcare.encounter.dto.request.EncounterRequest;
import com.healthcare.encounter.dto.request.EncounterStatusUpdateRequest;
import com.healthcare.encounter.dto.request.EncounterUpdateRequest;
import com.healthcare.encounter.dto.response.EncounterResponse;
import com.healthcare.encounter.entity.Encounter;
import com.healthcare.encounter.enums.EncounterStatus;
import com.healthcare.encounter.exception.DuplicateEncounterException;
import com.healthcare.encounter.exception.EncounterNotFoundException;
import com.healthcare.encounter.exception.InvalidEncounterStateException;
import com.healthcare.encounter.mapper.EncounterMapper;
import com.healthcare.encounter.repository.EncounterRepository;
import com.healthcare.encounter.service.EncounterService;
import java.util.Locale;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EncounterServiceImpl implements EncounterService {

    private static final String DEFAULT_SORT_FIELD = "id";
    private static final java.util.Set<String> ALLOWED_SORT_FIELDS = java.util.Set.of(
            "id",
            "encounterNumber",
            "appointmentId",
            "patientId",
            "doctorId",
            "encounterDate",
            "status",
            "createdAt",
            "updatedAt");

    private final EncounterRepository repository;
    private final EncounterMapper mapper;
    private final AppointmentServiceClient appointmentServiceClient;
    private final FhirIntegrationClient fhirIntegrationClient;

    public EncounterServiceImpl(
            EncounterRepository repository,
            EncounterMapper mapper,
            AppointmentServiceClient appointmentServiceClient,
            FhirIntegrationClient fhirIntegrationClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.appointmentServiceClient = appointmentServiceClient;
        this.fhirIntegrationClient = fhirIntegrationClient;
    }

    @Override
    @Transactional
    public EncounterResponse createEncounter(EncounterRequest request) {
        AppointmentResponse appointment = appointmentServiceClient.getAppointmentById(request.getAppointmentId());

        if (isCancelledOrNoShow(appointment.status())) {
            throw new InvalidEncounterStateException(
                    "Encounter cannot be created for a cancelled or no-show appointment");
        }

        if (repository.existsByAppointmentId(appointment.id())) {
            throw new DuplicateEncounterException(
                    "An encounter already exists for appointment id: " + appointment.id());
        }

        Encounter encounter = mapper.toEntity(request, appointment);
        encounter.setEncounterNumber(generateEncounterNumber());
        encounter.setStatus(EncounterStatus.OPEN);

        // Save in EncounterMS database
        Encounter saved = repository.save(encounter);

        // Send to FHIR Integration Service
        EncounterFhirRequest fhirRequest = new EncounterFhirRequest(
                saved.getId(),
                saved.getAppointmentId(),
                saved.getPatientId(),
                saved.getDoctorId(),
                saved.getEncounterDate(),
                saved.getStatus().name(),
                saved.getDiagnosis(),
                saved.getClinicalNotes());

        fhirIntegrationClient.createEncounter(fhirRequest);

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EncounterResponse getEncounterById(Long id) {
        return mapper.toResponse(findEncounter(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EncounterResponse> getAllEncounters(
            int page,
            int size,
            String sortBy,
            String direction) {

        Pageable pageable = createPageable(page, size, sortBy, direction);
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EncounterResponse> getEncountersByPatient(
            Long patientId,
            int page,
            int size,
            String sortBy,
            String direction) {

        Pageable pageable = createPageable(page, size, sortBy, direction);
        return repository.findByPatientId(patientId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EncounterResponse> getEncountersByDoctor(
            Long doctorId,
            int page,
            int size,
            String sortBy,
            String direction) {

        Pageable pageable = createPageable(page, size, sortBy, direction);
        return repository.findByDoctorId(doctorId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public EncounterResponse getEncounterByAppointment(Long appointmentId) {
        Encounter encounter = repository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new EncounterNotFoundException(
                        "Encounter not found for appointment id: " + appointmentId));
        return mapper.toResponse(encounter);
    }

    @Override
    @Transactional
    public EncounterResponse updateEncounter(
            Long id,
            EncounterUpdateRequest request) {

        Encounter encounter = findEncounter(id);
        validateEditableState(encounter);

        mapper.updateEntity(request, encounter);
        Encounter updated = repository.save(encounter);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public EncounterResponse updateEncounterStatus(
            Long id,
            EncounterStatusUpdateRequest request) {

        Encounter encounter = findEncounter(id);
        EncounterStatus currentStatus = encounter.getStatus();
        EncounterStatus requestedStatus = request.getStatus();

        if (currentStatus == EncounterStatus.COMPLETED
                && requestedStatus != EncounterStatus.COMPLETED) {
            throw new InvalidEncounterStateException(
                    "Completed encounter cannot be changed to " + requestedStatus);
        }

        if (currentStatus == EncounterStatus.CANCELLED
                && requestedStatus != EncounterStatus.CANCELLED) {
            throw new InvalidEncounterStateException(
                    "Cancelled encounter cannot be reopened");
        }

        encounter.setStatus(requestedStatus);
        Encounter updated = repository.save(encounter);
        return mapper.toResponse(updated);
    }

    private Encounter findEncounter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EncounterNotFoundException(
                        "Encounter not found with id: " + id));
    }

    private void validateEditableState(Encounter encounter) {
        if (encounter.getStatus() == EncounterStatus.COMPLETED) {
            throw new InvalidEncounterStateException(
                    "Completed encounter cannot be edited");
        }

        if (encounter.getStatus() == EncounterStatus.CANCELLED) {
            throw new InvalidEncounterStateException(
                    "Cancelled encounter cannot be edited");
        }
    }

    private boolean isCancelledOrNoShow(String status) {
        if (status == null) {
            return false;
        }

        String normalized = status.trim().toUpperCase(Locale.ROOT);
        return "CANCELLED".equals(normalized) || "NO_SHOW".equals(normalized);
    }

    private Pageable createPageable(
            int page,
            int size,
            String sortBy,
            String direction) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be greater than or equal to 0");
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }

        String effectiveSortBy = sortBy == null || sortBy.isBlank() ? DEFAULT_SORT_FIELD : sortBy;

        if (!ALLOWED_SORT_FIELDS.contains(effectiveSortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sortBy. Allowed values: " + ALLOWED_SORT_FIELDS);
        }

        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(effectiveSortBy).descending()
                : Sort.by(effectiveSortBy).ascending();

        return PageRequest.of(page, size, sort);
    }

    private String generateEncounterNumber() {
        return "ENC-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase(Locale.ROOT);
    }
}
