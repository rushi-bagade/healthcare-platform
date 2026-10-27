package com.healthcare.fhir.controller;

import org.hl7.fhir.r4.model.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.fhir.dto.PatientFhirRequest;
import com.healthcare.fhir.service.FhirPatientService;

@RestController
@RequestMapping("/v1/fhir/patients")
public class FhirPatientController {

    private final FhirPatientService fhirPatientService;

    public FhirPatientController(FhirPatientService fhirPatientService) {
        this.fhirPatientService = fhirPatientService;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(
            @RequestBody PatientFhirRequest request) {

      System.out.println(" -------  Inside FhirPatientController : createPatient ");          

        Patient patient = fhirPatientService.createPatient(request);

    System.out.println(" ========  End of  FhirPatientController : createPatient ");          

        return ResponseEntity.ok(patient);
    }
}