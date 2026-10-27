package com.healthcare.appointment.mapper;

import org.springframework.stereotype.Component;

import com.healthcare.appointment.dto.AppointmentRequest;
import com.healthcare.appointment.dto.AppointmentResponse;
import com.healthcare.appointment.entity.Appointment;

@Component
public class AppointmentMapper {

    public Appointment toEntity(AppointmentRequest request) {

        Appointment appointment = new Appointment();

        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setStartTime(request.getStartTime());
        appointment.setEndTime(request.getEndTime());
        appointment.setReason(request.getReason());

        return appointment;
    }

    public AppointmentResponse toResponse(Appointment appointment) {

        AppointmentResponse response = new AppointmentResponse();

        response.setId(appointment.getId());
        response.setAppointmentNumber(
                appointment.getAppointmentNumber());
        response.setPatientId(appointment.getPatientId());
        response.setDoctorId(appointment.getDoctorId());
        response.setAppointmentDate(
                appointment.getAppointmentDate());
        response.setStartTime(appointment.getStartTime());
        response.setEndTime(appointment.getEndTime());
        response.setReason(appointment.getReason());
        response.setStatus(appointment.getStatus());
        response.setCreatedAt(appointment.getCreatedAt());
        response.setUpdatedAt(appointment.getUpdatedAt());

        return response;
    }

    public void updateEntity(
            AppointmentRequest request,
            Appointment appointment) {

        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setAppointmentDate(
                request.getAppointmentDate());
        appointment.setStartTime(request.getStartTime());
        appointment.setEndTime(request.getEndTime());
        appointment.setReason(request.getReason());
    }
}