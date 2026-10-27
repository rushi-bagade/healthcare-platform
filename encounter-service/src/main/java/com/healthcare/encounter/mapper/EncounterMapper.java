package com.healthcare.encounter.mapper;

import com.healthcare.encounter.client.dto.AppointmentResponse;
import com.healthcare.encounter.dto.request.EncounterRequest;
import com.healthcare.encounter.dto.request.EncounterUpdateRequest;
import com.healthcare.encounter.dto.response.EncounterResponse;
import com.healthcare.encounter.entity.Encounter;
import org.springframework.stereotype.Component;

@Component
public class EncounterMapper {

    public Encounter toEntity(EncounterRequest request, AppointmentResponse appointment) {
        Encounter encounter = new Encounter();
        encounter.setAppointmentId(appointment.id());
        encounter.setPatientId(appointment.patientId());
        encounter.setDoctorId(appointment.doctorId());
        encounter.setEncounterDate(appointment.appointmentDate());
        encounter.setChiefComplaint(request.getChiefComplaint());
        encounter.setDiagnosis(request.getDiagnosis());
        encounter.setClinicalNotes(request.getClinicalNotes());
        encounter.setTreatmentNotes(request.getTreatmentNotes());
        return encounter;
    }

    public void updateEntity(EncounterUpdateRequest request, Encounter encounter) {
        encounter.setChiefComplaint(request.getChiefComplaint());
        encounter.setDiagnosis(request.getDiagnosis());
        encounter.setClinicalNotes(request.getClinicalNotes());
        encounter.setTreatmentNotes(request.getTreatmentNotes());
    }

    public EncounterResponse toResponse(Encounter encounter) {
        EncounterResponse response = new EncounterResponse();
        response.setId(encounter.getId());
        response.setEncounterNumber(encounter.getEncounterNumber());
        response.setAppointmentId(encounter.getAppointmentId());
        response.setPatientId(encounter.getPatientId());
        response.setDoctorId(encounter.getDoctorId());
        response.setEncounterDate(encounter.getEncounterDate());
        response.setChiefComplaint(encounter.getChiefComplaint());
        response.setDiagnosis(encounter.getDiagnosis());
        response.setClinicalNotes(encounter.getClinicalNotes());
        response.setTreatmentNotes(encounter.getTreatmentNotes());
        response.setStatus(encounter.getStatus());
        response.setCreatedAt(encounter.getCreatedAt());
        response.setUpdatedAt(encounter.getUpdatedAt());
        return response;
    }
}
