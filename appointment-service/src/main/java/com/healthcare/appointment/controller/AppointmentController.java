package com.healthcare.appointment.controller;

import com.healthcare.appointment.dto.AppointmentResponse;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.healthcare.appointment.entity.Appointment;
import com.healthcare.appointment.dto.AppointmentStatusUpdateRequest;
import com.healthcare.appointment.dto.AppointmentRequest;
import com.healthcare.appointment.service.AppointmentService;
import com.healthcare.appointment.dto.RescheduleAppointmentRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private AppointmentService service;

    AppointmentController(AppointmentService service) {

        this.service = service;
    }

    @GetMapping("/test")
    public String test() {
        return "Appointment Controller Working";
    }

    @PostMapping("/test")
    public String postTest() {
        return "POST working";
    }

    @PostMapping
    public ResponseEntity<String> createAppointment(@RequestBody @Valid AppointmentRequest request) {

        String str = service.createAppointment(request);

        return new ResponseEntity<>(str, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {

    AppointmentResponse response = service.getAppointmentById(id);

    return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> getAllAppointments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "appointmentDate") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {

        Page<AppointmentResponse> response =
            service.getAllAppointments(
                    page,
                    size,
                    sortBy,
                    direction);

    return ResponseEntity.ok(response);
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<Page<AppointmentResponse>>
        getAppointmentsByPatient(
        @PathVariable Long patientId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "appointmentDate") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {

    Page<AppointmentResponse> response = service.getAppointmentsByPatient(
                    patientId,
                    page,
                    size,
                    sortBy,
                    direction);

    return ResponseEntity.ok(response);
    }

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<Page<AppointmentResponse>> getAppointmentsByDoctor(
        @PathVariable Long doctorId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "appointmentDate") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {

    Page<AppointmentResponse> response =
            service.getAppointmentsByDoctor(
                    doctorId,
                    page,
                    size,
                    sortBy,
                    direction);

    return ResponseEntity.ok(response);
    }

   @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse> updateAppointmentStatus(
        @PathVariable Long id,
        @Valid @RequestBody AppointmentStatusUpdateRequest request) {

    AppointmentResponse response = service.updateAppointmentStatus(id, request);

    return ResponseEntity.ok(response);
}

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancelAppointment(
        @PathVariable Long id) {

    AppointmentResponse response =
            service.cancelAppointment(id);

    return ResponseEntity.ok(response);
}

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<AppointmentResponse> rescheduleAppointment(
        @PathVariable Long id,
        @Valid @RequestBody RescheduleAppointmentRequest request) {

    AppointmentResponse response =
            service.rescheduleAppointment(id, request);

    return ResponseEntity.ok(response);
}
}
