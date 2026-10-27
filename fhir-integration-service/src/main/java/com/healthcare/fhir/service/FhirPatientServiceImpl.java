package com.healthcare.fhir.service;

import org.hl7.fhir.r4.model.Patient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.healthcare.fhir.client.HapiFhirClient;
import com.healthcare.fhir.dto.PatientCreatedEvent;
import com.healthcare.fhir.dto.PatientFhirRequest;
import com.healthcare.fhir.mapper.PatientFhirMapper;
import com.healthcare.fhir.service.FhirPatientService;

@Service
public class FhirPatientServiceImpl implements FhirPatientService {

    private final PatientFhirMapper patientFhirMapper;
    private final HapiFhirClient hapiFhirClient;

    public FhirPatientServiceImpl(
            PatientFhirMapper patientFhirMapper,
            HapiFhirClient hapiFhirClient) {

        this.patientFhirMapper = patientFhirMapper;
        this.hapiFhirClient = hapiFhirClient;
    }

    @Override
    public Patient createPatient(PatientFhirRequest request) {

    System.out.println(" -------  Inside FhirPatientServiceImpl : createPatient ");          

        // 1. Convert our DTO to FHIR Patient
        Patient patient = patientFhirMapper.toFhirPatient(request);

    System.out.println(" ========  End of  FhirPatientServiceImpl : createPatient ");          

        // 2. Send FHIR Patient to HAPI FHIR server
        return hapiFhirClient.savePatient(patient);
    }


   
}