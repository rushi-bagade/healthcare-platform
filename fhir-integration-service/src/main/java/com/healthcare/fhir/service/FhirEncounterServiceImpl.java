package com.healthcare.fhir.service;

import com.healthcare.fhir.client.HapiFhirClient;
import com.healthcare.fhir.dto.EncounterFhirRequest;
import com.healthcare.fhir.mapper.EncounterFhirMapper;
import com.healthcare.fhir.service.FhirEncounterService;

import org.hl7.fhir.r4.model.Encounter;
import org.springframework.stereotype.Service;

@Service
public class FhirEncounterServiceImpl implements FhirEncounterService {

    private final EncounterFhirMapper mapper;
    private final HapiFhirClient hapiFhirClient;

    public FhirEncounterServiceImpl(
            EncounterFhirMapper mapper,
            HapiFhirClient hapiFhirClient) {
        this.mapper = mapper;
        this.hapiFhirClient = hapiFhirClient;
    }

    @Override
    public Encounter createEncounter(EncounterFhirRequest request) {

        // Convert our DTO into FHIR Encounter
        Encounter fhirEncounter = mapper.toFhirEncounter(request);

        // Send FHIR Encounter to HAPI FHIR
        // return hapiFhirClient.createEncounter(fhirEncounter);
        return hapiFhirClient.saveEncounter(fhirEncounter);
    }

    @Override
    public Encounter getEncounter(String id) {

        // Get FHIR Encounter from HAPI FHIR
        return hapiFhirClient.getEncounter(id);
    }
}