package com.healthcare.encounter.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.healthcare.encounter.dto.request.EncounterFhirRequest;

@Component
public class FhirIntegrationClient {

    private final RestClient fhirRestClient;

    public FhirIntegrationClient(RestClient fhirRestClient) {
        this.fhirRestClient = fhirRestClient;
    }

    public void createEncounter(EncounterFhirRequest request) {

        fhirRestClient.post()
                .uri("/v1/fhir/encounters")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}