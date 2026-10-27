package com.healthcare.fhir.controller;

import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.healthcare.fhir.dto.PractitionerFhirRequest;
import com.healthcare.fhir.service.FhirPractitionerService;

@RestController
@RequestMapping("/v1/fhir/practitioners")
public class FhirPractitionerController {

    private final FhirPractitionerService fhirPractitionerService;

    public FhirPractitionerController(
            FhirPractitionerService fhirPractitionerService) {

        this.fhirPractitionerService = fhirPractitionerService;
    }

    @PostMapping
    public ResponseEntity<Practitioner> createPractitioner(
            @RequestBody PractitionerFhirRequest request) {

        Practitioner practitioner = fhirPractitionerService.createPractitioner(request);

        return ResponseEntity.ok(practitioner);
    }
}
