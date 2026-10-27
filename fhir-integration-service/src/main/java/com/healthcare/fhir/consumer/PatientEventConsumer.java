package com.healthcare.fhir.consumer;

import com.healthcare.fhir.dto.PatientCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.healthcare.fhir.service.FhirPatientService;
import com.healthcare.fhir.dto.PatientFhirRequest;

@Component
public class PatientEventConsumer {

    // @KafkaListener(topics = "patient-created")
    // public void consumePatientCreated(PatientCreatedEvent event) {
    //     System.out.println("Received PatientCreated event");
    //     System.out.println("Patient ID: " + event.patientId());
    //     System.out.println("Patient name: " + event.firstName() + " " + event.lastName());
    // }
    
    private final FhirPatientService fhirPatientService;

    public PatientEventConsumer(FhirPatientService fhirPatientService) {
        this.fhirPatientService = fhirPatientService;
    }

    @KafkaListener(topics = "patient-created")
    public void consumePatientCreated(PatientCreatedEvent event) {

        // boolean testRetry = true; // Set false after testing

        // if (testRetry) {
        //     throw new RuntimeException("Testing Kafka retries");
        // }

        System.out.println(
                "Received PatientCreated event: " + event.patientId()
        );

        PatientFhirRequest request = new PatientFhirRequest(
                event.patientId(),
                event.firstName(),
                event.lastName(),
                event.dateOfBirth(),
                event.gender(),
                event.phoneNumber(),
                event.email(),
                event.address(),
                event.bloodGroup()
        );

        fhirPatientService.createPatient(request);

        System.out.println(
                "Patient synchronized to HAPI FHIR: " + event.patientId()
        );
    }
}