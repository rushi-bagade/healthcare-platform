package com.healthcare.fhir.mapper;

import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.CodeableConcept;

import org.springframework.stereotype.Component;

import com.healthcare.fhir.dto.PractitionerFhirRequest;

@Component
public class PractitionerFhirMapper {

    public Practitioner toFhirPractitioner(
            PractitionerFhirRequest request) {

        Practitioner practitioner = new Practitioner();

        // Keep our internal doctor ID as the FHIR resource ID
        practitioner.setId("doctor-" + request.doctorId());
        // Name
        HumanName name = practitioner.addName();
        name.setFamily(request.lastName());
        name.addGiven(request.firstName());

        // Gender
        if (request.gender() != null) {

            switch (request.gender().toUpperCase()) {

                case "MALE" ->
                    practitioner.setGender(
                            Enumerations.AdministrativeGender.MALE);

                case "FEMALE" ->
                    practitioner.setGender(
                            Enumerations.AdministrativeGender.FEMALE);

                case "OTHER" ->
                    practitioner.setGender(
                            Enumerations.AdministrativeGender.OTHER);

                default ->
                    practitioner.setGender(
                            Enumerations.AdministrativeGender.UNKNOWN);
            }
        }

        // Phone
        if (request.phoneNumber() != null) {

            practitioner.addTelecom()
                    .setSystem(ContactPoint.ContactPointSystem.PHONE)
                    .setValue(request.phoneNumber());
        }

        // Email
        if (request.email() != null) {

            practitioner.addTelecom()
                    .setSystem(ContactPoint.ContactPointSystem.EMAIL)
                    .setValue(request.email());
        }

        // Specialization
        if (request.specialization() != null
                && !request.specialization().isBlank()) {

            practitioner.addQualification()
                    .setCode(
                            new CodeableConcept()
                                    .setText(request.specialization()));
        }

        return practitioner;
    }
}