package com.healthcare.fhir.service;

import org.hl7.fhir.r4.model.Practitioner;

import com.healthcare.fhir.dto.PractitionerFhirRequest;

public interface FhirPractitionerService {

    Practitioner createPractitioner(
            PractitionerFhirRequest request);
}