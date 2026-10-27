package com.healthcare.fhir.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FhirConfig {

    @Bean
    public FhirContext fhirContext() {
        return FhirContext.forR4(); // Our application will work with FHIR R4.
    }

    @Bean
    public IGenericClient fhirClient(
            FhirContext fhirContext,
            @Value("${fhir.server.base-url}") String baseUrl) {

        return fhirContext.newRestfulGenericClient(baseUrl); // creates a client that knows how to communicate with:
    }
}
// Spring Boot -> FhirContext R4 -> IGenericClient -> HAPI FHIR Server
