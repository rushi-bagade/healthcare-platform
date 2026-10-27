package com.healthcare.profile.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.healthcare.profile.doctorDto.DoctorPatchRequest;
import com.healthcare.profile.doctorDto.DoctorRequest;
import com.healthcare.profile.doctorDto.DoctorResponse;
import com.healthcare.profile.doctorDto.DoctorStatusUpdateRequest;

public interface DoctorService {

    DoctorResponse createDoctor(
            DoctorRequest request);

    DoctorResponse getDoctorById(
            Long id);

    Page<DoctorResponse> getAllDoctors(
            int page,
            int size,
            String sortBy,
            String direction);

    DoctorResponse updateDoctor(
            Long id,
            DoctorRequest request);

    DoctorResponse updateDoctorPartially(
            Long id,
            DoctorPatchRequest request);

    DoctorResponse updateDoctorStatus(
            Long id,
            DoctorStatusUpdateRequest request);

    List<DoctorResponse> searchDoctors(
            String firstName,
            String lastName,
            String specialization,
            String qualification,
            String licenseNumber,
            String phoneNumber,
            String email);

    String deleteDoctor(Long id);
}