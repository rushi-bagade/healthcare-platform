package com.healthcare.profile.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.healthcare.profile.entity.Doctor;
import com.healthcare.profile.doctorDto.PractitionerFhirRequest;

import com.healthcare.profile.dto.PatientFhirRequest;

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

//     public void createPatient(PatientFhirRequest request) {   
//         System.out.println(" -------  Inside FhirIntegrationClient: createPatient ");
//         restClient.post()
//                 .uri("/v1/fhir/patients")
//                 .body(request)
//                 .retrieve()
//                 .toBodilessEntity();
//         System.out.println(" ========  End of FhirIntegrationClient: createPatient ");
//     }

        public void createPractitioner(Doctor doctor) {

        PractitionerFhirRequest request =
                new PractitionerFhirRequest(
                        doctor.getId(),
                        doctor.getFirstName(),
                        doctor.getLastName(),
                        doctor.getGender(),
                        doctor.getPhoneNumber(),
                        doctor.getEmail(),
                        doctor.getSpecialization()
                );

        restClient.post()
                .uri("/v1/fhir/practitioners")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
