package com.healthcare.fhir.service;

import org.hl7.fhir.r4.model.Appointment;
import org.springframework.stereotype.Service;

import com.healthcare.fhir.client.HapiFhirClient;
import com.healthcare.fhir.dto.AppointmentFhirRequest;
import com.healthcare.fhir.mapper.AppointmentFhirMapper;
import com.healthcare.fhir.service.FhirAppointmentService;

@Service
public class FhirAppointmentServiceImpl implements FhirAppointmentService {

    private final AppointmentFhirMapper appointmentFhirMapper;
    private final HapiFhirClient hapiFhirClient;

    public FhirAppointmentServiceImpl(
            AppointmentFhirMapper appointmentFhirMapper,
            HapiFhirClient hapiFhirClient) {

        this.appointmentFhirMapper = appointmentFhirMapper;
        this.hapiFhirClient = hapiFhirClient;
    }

    @Override
    public Appointment createAppointment(
            AppointmentFhirRequest request) {

        Appointment appointment = appointmentFhirMapper.toFhirAppointment(request);

        return hapiFhirClient.saveAppointment(appointment);
    }
}