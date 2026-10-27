package com.healthcare.fhir.mapper;

import com.healthcare.fhir.dto.EncounterFhirRequest;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class EncounterFhirMapper {

        public Encounter toFhirEncounter(EncounterFhirRequest request) {

                Encounter encounter = new Encounter();

                // FHIR resource ID
                // encounter.setId(request.encounterId().toString());
                encounter.setId("encounter-" + request.encounterId());

                // Patient reference
                encounter.setSubject(
                                new Reference("Patient/patient-" + request.patientId()));
                // Doctor reference
                // encounter.addParticipant()
                // .setIndividual(
                // new Reference("Practitioner/" + request.doctorId()));

                encounter.addParticipant()
                                .setIndividual(
                                                new Reference("Practitioner/doctor-" + request.doctorId()));

                // Link to appointment
                // encounter.addAppointment(
                // new Reference("Appointment/" + request.appointmentId()));

                encounter.addAppointment(
                                new Reference("Appointment/appointment-" + request.appointmentId()));

                // Encounter status
                if ("COMPLETED".equalsIgnoreCase(request.status())) {
                        encounter.setStatus(Encounter.EncounterStatus.FINISHED);
                } else if ("CANCELLED".equalsIgnoreCase(request.status())) {
                        encounter.setStatus(Encounter.EncounterStatus.CANCELLED);
                } else {
                        encounter.setStatus(Encounter.EncounterStatus.INPROGRESS);
                }

                // Encounter date
                if (request.encounterDate() != null) {
                        encounter.setPeriod(
                                        new Period()
                                                        .setStart(Date.valueOf(request.encounterDate())));
                }

                // Diagnosis / reason
                if (request.diagnosis() != null &&
                                !request.diagnosis().isBlank()) {

                        CodeableConcept reason = new CodeableConcept()
                                        .setText(request.diagnosis());

                        encounter.addReasonCode(reason);
                }

                // Clinical notes
                if (request.clinicalNotes() != null &&
                                !request.clinicalNotes().isBlank()) {

                        encounter.getText().setStatus(
                                        org.hl7.fhir.r4.model.Narrative.NarrativeStatus.GENERATED);

                        encounter.getText().setDivAsString(
                                        "<div>" + request.clinicalNotes() + "</div>");
                }

                return encounter;
        }
}

// Your application has your own Java model, while FHIR has a standard
// healthcare model.

// FHIR defines a standard resource called: FHIR Encounter
// And HAPI FHIR gives us a Java class for it: org.hl7.fhir.r4.model.Encounter
// So when we write: Encounter encounter = new Encounter();
// in the FHIR service, this is not your Encounter entity.
// It is: org.hl7.fhir.r4.model.Encounter
