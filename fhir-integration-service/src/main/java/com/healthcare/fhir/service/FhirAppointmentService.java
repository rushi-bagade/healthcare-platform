package com.healthcare.fhir.service;

import org.hl7.fhir.r4.model.Appointment;

import com.healthcare.fhir.dto.AppointmentFhirRequest;

public interface FhirAppointmentService {

    Appointment createAppointment(AppointmentFhirRequest request);

}