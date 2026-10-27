package com.healthcare.profile.mapper;

import org.springframework.stereotype.Component;

import com.healthcare.profile.doctorDto.DoctorRequest;
import com.healthcare.profile.doctorDto.DoctorResponse;
import com.healthcare.profile.entity.Doctor;

@Component
public class DoctorMapper {

    public Doctor toEntity(DoctorRequest request) {

        Doctor doctor = new Doctor();

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setGender(request.getGender());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setEmail(request.getEmail());
        doctor.setAddress(request.getAddress());

        return doctor;
    }

    public DoctorResponse toResponse(Doctor doctor) {

        DoctorResponse response = new DoctorResponse();

        response.setId(doctor.getId());
        response.setDoctorNumber(doctor.getDoctorNumber());
        response.setFirstName(doctor.getFirstName());
        response.setLastName(doctor.getLastName());
        response.setDateOfBirth(doctor.getDateOfBirth());
        response.setGender(doctor.getGender());
        response.setSpecialization(doctor.getSpecialization());
        response.setQualification(doctor.getQualification());
        response.setLicenseNumber(doctor.getLicenseNumber());
        response.setExperienceYears(doctor.getExperienceYears());
        response.setPhoneNumber(doctor.getPhoneNumber());
        response.setEmail(doctor.getEmail());
        response.setAddress(doctor.getAddress());
        response.setStatus(doctor.getStatus());
        response.setCreatedAt(doctor.getCreatedAt());
        response.setUpdatedAt(doctor.getUpdatedAt());

        return response;
    }

    public void updateEntity(
            DoctorRequest request,
            Doctor doctor) {

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setGender(request.getGender());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setEmail(request.getEmail());
        doctor.setAddress(request.getAddress());
    }
}