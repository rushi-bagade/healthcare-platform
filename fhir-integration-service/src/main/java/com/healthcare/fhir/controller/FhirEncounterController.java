package com.healthcare.fhir.controller;

import com.healthcare.fhir.dto.EncounterFhirRequest;
import com.healthcare.fhir.service.FhirEncounterService;

import org.hl7.fhir.r4.model.Encounter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/fhir/encounters")
public class FhirEncounterController {

    private final FhirEncounterService service;

    public FhirEncounterController(FhirEncounterService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Encounter> createEncounter(
            @RequestBody EncounterFhirRequest request) {

        Encounter response = service.createEncounter(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Encounter> getEncounter(
            @PathVariable String id) {

        Encounter response = service.getEncounter(id);

        return ResponseEntity.ok(response);
    }
}