package com.healthcare.fhir.mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.stereotype.Component;

import com.healthcare.fhir.dto.AppointmentFhirRequest;

@Component
public class AppointmentFhirMapper {

    public Appointment toFhirAppointment(AppointmentFhirRequest request) {

        Appointment appointment = new Appointment();

        // Internal appointment ID → FHIR resource ID
        appointment.setId("appointment-" + request.appointmentId());

        // Appointment number
        if (request.appointmentNumber() != null) {
            appointment.addIdentifier()
                    .setValue(request.appointmentNumber());
        }

        // Appointment status
        if (request.status() != null) {

            switch (request.status().toUpperCase()) {

                case "SCHEDULED", "CONFIRMED", "RESCHEDULED" ->
                    appointment.setStatus(
                            Appointment.AppointmentStatus.BOOKED);

                case "COMPLETED" ->
                    appointment.setStatus(
                            Appointment.AppointmentStatus.FULFILLED);

                case "CANCELLED" ->
                    appointment.setStatus(
                            Appointment.AppointmentStatus.CANCELLED);

                case "NO_SHOW" ->
                    appointment.setStatus(
                            Appointment.AppointmentStatus.NOSHOW);

                default ->
                    appointment.setStatus(
                            Appointment.AppointmentStatus.PROPOSED);
            }
        }

        // Patient reference
        appointment.addParticipant()
                .setActor(
                        new Reference(
                                "Patient/patient-" + request.patientId()));

        // Doctor reference
        appointment.addParticipant()
                .setActor(
                        new Reference(
                                "Practitioner/doctor-" + request.doctorId()));

        // Start time
        if (request.appointmentDate() != null
                && request.startTime() != null) {

            LocalDateTime start = LocalDateTime.of(
                    request.appointmentDate(),
                    request.startTime());

            appointment.setStart(
                    Timestamp.valueOf(start));
        }

        // End time
        if (request.appointmentDate() != null
                && request.endTime() != null) {

            LocalDateTime end = LocalDateTime.of(
                    request.appointmentDate(),
                    request.endTime());

            appointment.setEnd(
                    Timestamp.valueOf(end));
        }

        // Reason
        if (request.reason() != null
                && !request.reason().isBlank()) {

            appointment.setDescription(request.reason());
        }

        return appointment;
    }
}