package com.healthcare.fhir.mapper;

import com.healthcare.fhir.dto.PatientFhirRequest;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Address;

@Component
public class PatientFhirMapper {

    public Patient toFhirPatient(PatientFhirRequest request) {

    System.out.println(" -------  Inside PatientFhirMapper : toFhirPatient ");          

        Patient patient = new Patient();

        patient.setId("patient-" + request.patientId());
        HumanName name = patient.addName();
        name.setFamily(request.lastName());
        name.addGiven(request.firstName());

        if (request.dateOfBirth() != null) {
            patient.setBirthDate(
                    java.sql.Date.valueOf(request.dateOfBirth()));
        }

        if (request.gender() != null) {
            switch (request.gender().toUpperCase()) {
                case "MALE" -> patient.setGender(
                        Enumerations.AdministrativeGender.MALE);

                case "FEMALE" -> patient.setGender(
                        Enumerations.AdministrativeGender.FEMALE);

                case "OTHER" -> patient.setGender(
                        Enumerations.AdministrativeGender.OTHER);

                default -> patient.setGender(
                        Enumerations.AdministrativeGender.UNKNOWN);
            }
        }

        if (request.phoneNumber() != null) {
            patient.addTelecom()
                    .setSystem(ContactPoint.ContactPointSystem.PHONE)
                    .setValue(request.phoneNumber());
        }

        if (request.email() != null) {
            patient.addTelecom()
                    .setSystem(ContactPoint.ContactPointSystem.EMAIL)
                    .setValue(request.email());
        }

        if (request.address() != null && !request.address().isBlank()) {
            Address address = patient.addAddress();
            address.setText(request.address());
        }


            System.out.println(" ========  End Of PatientFhirMapper : toFhirPatient ");          

        return patient;
    }
}