package com.healthcare.fhir.service;

import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.stereotype.Service;

import com.healthcare.fhir.client.HapiFhirClient;
import com.healthcare.fhir.dto.PractitionerFhirRequest;
import com.healthcare.fhir.mapper.PractitionerFhirMapper;
import com.healthcare.fhir.service.FhirPractitionerService;

@Service
public class FhirPractitionerServiceImpl
        implements FhirPractitionerService {

    private final PractitionerFhirMapper practitionerFhirMapper;
    private final HapiFhirClient hapiFhirClient;

    public FhirPractitionerServiceImpl(
            PractitionerFhirMapper practitionerFhirMapper,
            HapiFhirClient hapiFhirClient) {

        this.practitionerFhirMapper = practitionerFhirMapper;
        this.hapiFhirClient = hapiFhirClient;
    }

    public Practitioner createPractitioner(
            PractitionerFhirRequest request) {

        // 1. Convert DTO to FHIR Practitioner
        Practitioner practitioner = practitionerFhirMapper.toFhirPractitioner(request);

        // 2. Send FHIR Practitioner to HAPI FHIR
        return hapiFhirClient.savePractitioner(practitioner);
    }
}