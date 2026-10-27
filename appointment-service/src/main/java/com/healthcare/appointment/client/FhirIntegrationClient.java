package com.healthcare.appointment.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.healthcare.appointment.dto.AppointmentFhirRequest;

@Component
public class FhirIntegrationClient {

    private final RestClient restClient;

    public FhirIntegrationClient(
            RestClient.Builder builder,
            @Value("${services.fhir.base-url}") String baseUrl) {

        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public void createAppointment(
            AppointmentFhirRequest request) {
 System.out.println("Sending appointment to FHIR Integration: "
            + request.appointmentId());

        restClient.post()
                .uri("/v1/fhir/appointments")
                .body(request)
                .retrieve()
                .toBodilessEntity();

                System.out.println("Appointment synchronized with FHIR Integration");
    }
}
