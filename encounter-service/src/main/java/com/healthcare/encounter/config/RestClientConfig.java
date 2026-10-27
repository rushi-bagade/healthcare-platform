package com.healthcare.encounter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient appointmentRestClient(
            RestClient.Builder builder,
            @Value("${services.appointment.base-url}") String baseUrl) {
        return builder
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    RestClient fhirRestClient(
            RestClient.Builder builder,
            @Value("${services.fhir.base-url}") String baseUrl) {

        return builder
                .baseUrl(baseUrl)
                .build();
    }
}
