package com.healthcare.appointment.service;

import org.springframework.data.domain.Page;
import com.healthcare.appointment.dto.RescheduleAppointmentRequest;
import com.healthcare.appointment.dto.AppointmentRequest;
import com.healthcare.appointment.dto.AppointmentResponse;
import com.healthcare.appointment.dto.AppointmentStatusUpdateRequest;

public interface AppointmentService {

    String createAppointment(AppointmentRequest request);

    AppointmentResponse getAppointmentById(Long id);

    Page<AppointmentResponse> getAllAppointments(
            int page,
            int size,
            String sortBy,
            String direction);

    Page<AppointmentResponse> getAppointmentsByPatient(
            Long patientId,
            int page,
            int size,
            String sortBy,
            String direction);

    Page<AppointmentResponse> getAppointmentsByDoctor(
            Long doctorId,
            int page,
            int size,
            String sortBy,
            String direction);

    AppointmentResponse updateAppointmentStatus(
            Long id,
            AppointmentStatusUpdateRequest request);

    AppointmentResponse cancelAppointment(Long id);
    
    AppointmentResponse rescheduleAppointment(
        Long id,
        RescheduleAppointmentRequest request);
}
