package com.healthcare.fhir.client;

import ca.uhn.fhir.rest.client.api.IGenericClient;

import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.stereotype.Component;
import org.hl7.fhir.r4.model.Practitioner;

@Component
public class HapiFhirClient { // It is your communication layer with HAPI FHIR.

    // FHIR Integration Service -> HapiFhirClient -> IGenericClient -> HAPI FHIR
    // Server

    private final IGenericClient client;

    public HapiFhirClient(IGenericClient client) {
        this.client = client;
    }

    public Encounter getEncounter(String id) {
        return client
                .read()
                .resource(Encounter.class)
                .withId(id)
                .execute();
    }

    public Patient savePatient(Patient patient) {
    System.out.println(" -------  Inside HapiFhirClient : savePatient ");          


        return (Patient) client
                .update()
                .resource(patient)
                .execute()
                .getResource();
    }

    public Practitioner savePractitioner(Practitioner practitioner) {

        // Use update() because we want to maintain
        // our internal doctor ID as the FHIR resource ID.
        return (Practitioner) client.update()
                .resource(practitioner)
                .execute()
                .getResource();
    }

    public Appointment saveAppointment(Appointment appointment) {

        // Use update() because we want to maintain
        // our internal appointment ID as the FHIR resource ID.
        return (Appointment) client.update()
                .resource(appointment)
                .execute()
                .getResource();
    }

    public Encounter saveEncounter(Encounter encounter) {

        // Use update() because we want to maintain
        // our internal encounter ID as the FHIR resource ID.
        return (Encounter) client.update()
                .resource(encounter)
                .execute()
                .getResource();
    }
}