package com.healthcare.profile.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.profile.dto.PatientPatchRequest;
import com.healthcare.profile.dto.PatientRequest;
import com.healthcare.profile.dto.PatientResponse;
import com.healthcare.profile.dto.StatusUpdateRequest;
import com.healthcare.profile.service.PatientProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/profiles/patients")
public class PatientProfileController {

    private PatientProfileService service;

    public PatientProfileController(PatientProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> createNewPatient(@RequestBody @Valid PatientRequest request) {

        String str = service.createNewPatient(request);

        return new ResponseEntity<>(str, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {

        PatientResponse p = service.getPatientById(id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<PatientResponse> page2 = service.getAllPatients(page, size, sortBy, direction);

        return new ResponseEntity<>(page2, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatientPartially(@PathVariable Long id,
            @RequestBody @Valid PatientPatchRequest request) {

         PatientResponse s = service.updatePatientPartially(id, request);
        return new ResponseEntity<>(s, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(
            @PathVariable Long id,
            @RequestBody PatientRequest request) {

        PatientResponse response = service.updatePatient(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PatientResponse> updatePatientStatus(@PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request) {

        PatientResponse response = service.updatePatientStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PatientResponse>> searchPatient(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email) {

        List<PatientResponse> patients = service.searchPatients(firstName, lastName, phoneNumber, email);

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {

        String str = service.deletePatient(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

}
