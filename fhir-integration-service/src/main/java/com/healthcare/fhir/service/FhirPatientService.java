package com.healthcare.fhir.service;

import org.hl7.fhir.r4.model.Patient;

import com.healthcare.fhir.dto.PatientFhirRequest;

public interface FhirPatientService {

    Patient createPatient(PatientFhirRequest request);
}