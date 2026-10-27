package com.healthcare.encounter.client;

import com.healthcare.encounter.client.dto.AppointmentResponse;
import com.healthcare.encounter.exception.AppointmentNotFoundException;
import com.healthcare.encounter.exception.ExternalServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.ResourceAccessException;

@Component
public class AppointmentServiceClient {   //Communicates with appointment service

    private final RestClient restClient;

    public AppointmentServiceClient(RestClient appointmentRestClient) {
        this.restClient = appointmentRestClient;
    }

    public AppointmentResponse getAppointmentById(Long appointmentId) {
        try {
            AppointmentResponse response = restClient.get()
                    .uri("/appointments/{id}", appointmentId)
                    .retrieve()
                    .body(AppointmentResponse.class);

            if (response == null) {
                throw new ExternalServiceException("Appointment service returned an empty response");
            }

            return response;
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode().value() == 404) {
                throw new AppointmentNotFoundException(
                        "Appointment not found with id: " + appointmentId);
            }
            throw new ExternalServiceException(
                    "Appointment service returned status " + ex.getStatusCode().value());
        } catch (ResourceAccessException ex) {
            throw new ExternalServiceException(
                    "Unable to connect to Appointment Service");
        }
    }
}
