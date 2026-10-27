package com.healthcare.encounter.controller;

import com.healthcare.encounter.dto.request.EncounterRequest;
import com.healthcare.encounter.dto.request.EncounterStatusUpdateRequest;
import com.healthcare.encounter.dto.request.EncounterUpdateRequest;
import com.healthcare.encounter.dto.response.EncounterResponse;
import com.healthcare.encounter.service.EncounterService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/encounters")
public class EncounterController {

    private final EncounterService service;

    public EncounterController(EncounterService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EncounterResponse> createEncounter(
            @Valid @RequestBody EncounterRequest request) {

        EncounterResponse response = service.createEncounter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncounterResponse> getEncounterById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getEncounterById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EncounterResponse>> getAllEncounters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                service.getAllEncounters(page, size, sortBy, direction));
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<Page<EncounterResponse>> getByPatient(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "encounterDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        return ResponseEntity.ok(
                service.getEncountersByPatient(
                        patientId, page, size, sortBy, direction));
    }

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<Page<EncounterResponse>> getByDoctor(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "encounterDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        return ResponseEntity.ok(
                service.getEncountersByDoctor(
                        doctorId, page, size, sortBy, direction));
    }

    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<EncounterResponse> getByAppointment(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                service.getEncounterByAppointment(appointmentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EncounterResponse> updateEncounter(
            @PathVariable Long id,
            @Valid @RequestBody EncounterUpdateRequest request) {

        return ResponseEntity.ok(service.updateEncounter(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EncounterResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody EncounterStatusUpdateRequest request) {

        return ResponseEntity.ok(service.updateEncounterStatus(id, request));
    }
}
