package com.healthcare.fhir.service;

import com.healthcare.fhir.dto.EncounterFhirRequest;
import org.hl7.fhir.r4.model.Encounter;

public interface FhirEncounterService {

    Encounter createEncounter(EncounterFhirRequest request);

    Encounter getEncounter(String id);
}