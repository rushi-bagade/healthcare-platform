package com.healthcare.fhir.controller;

import org.hl7.fhir.r4.model.Appointment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.healthcare.fhir.dto.AppointmentFhirRequest;
import com.healthcare.fhir.service.FhirAppointmentService;

@RestController
@RequestMapping("/v1/fhir/appointments")
public class FhirAppointmentController {

    private final FhirAppointmentService fhirAppointmentService;

    public FhirAppointmentController(
            FhirAppointmentService fhirAppointmentService) {

        this.fhirAppointmentService = fhirAppointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody AppointmentFhirRequest request) {

        Appointment appointment = fhirAppointmentService.createAppointment(request);

        return ResponseEntity.ok(appointment);
    }
}