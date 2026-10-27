package com.healthcare.appointment.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class ProfileServiceClient {

    private final RestClient restClient;

    public ProfileServiceClient() {

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }

    public boolean patientExists(Long patientId) {

        try {
            restClient.get()
                    .uri("/v1/profiles/patients/{id}", patientId)
                    .retrieve()
                    .toBodilessEntity();
            return true;

        } catch (RestClientResponseException ex) {

            if (ex.getStatusCode().value() == 404) {
                return false;
            }

            throw ex;
        }
    }

    public boolean doctorExists(Long doctorId) {

        try {
            restClient.get()
                    .uri("/v1/profiles/doctors/{id}", doctorId)
                    .retrieve()
                    .toBodilessEntity();
            return true;

        } catch (RestClientResponseException ex) {

            if (ex.getStatusCode().value() == 404) {
                return false;
            }

            throw ex;
        }
    }

}
