package com.healthcare.profile.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.healthcare.profile.doctorDto.DoctorPatchRequest;
import com.healthcare.profile.doctorDto.DoctorRequest;
import com.healthcare.profile.doctorDto.DoctorResponse;
import com.healthcare.profile.doctorDto.DoctorStatusUpdateRequest;
import com.healthcare.profile.entity.Doctor;
import com.healthcare.profile.doctorDto.DoctorStatus;
import com.healthcare.profile.exception.DoctorNotFoundException;
import com.healthcare.profile.exception.DuplicateDoctorException;
import com.healthcare.profile.mapper.DoctorMapper;
import com.healthcare.profile.repository.DoctorRepository;
import com.healthcare.profile.client.FhirIntegrationClient;
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper mapper;
    private final FhirIntegrationClient fhirIntegrationClient;
   
        public DoctorServiceImpl(
            DoctorRepository doctorRepository,
            DoctorMapper mapper,
        FhirIntegrationClient fhirIntegrationClient) {

        this.doctorRepository = doctorRepository;
        this.mapper = mapper;
        this.fhirIntegrationClient = fhirIntegrationClient;
    }

    @Override
    public DoctorResponse createDoctor(
            DoctorRequest request) {

        if (doctorRepository.existsByPhoneNumber(
                request.getPhoneNumber())) {

            throw new DuplicateDoctorException(
                    "Doctor with phone number "
                            + request.getPhoneNumber()
                            + " already exists");
        }

        if (request.getEmail() != null
                && doctorRepository.existsByEmail(
                        request.getEmail())) {

            throw new DuplicateDoctorException(
                    "Doctor with email "
                            + request.getEmail()
                            + " already exists");
        }

        if (doctorRepository.existsByLicenseNumber(
                request.getLicenseNumber())) {

            throw new DuplicateDoctorException(
                    "Doctor with license number "
                            + request.getLicenseNumber()
                            + " already exists");
        }

        Doctor doctor = mapper.toEntity(request);

        doctor.setDoctorNumber(
                generateDoctorNumber());

        doctor.setStatus(DoctorStatus.ACTIVE);

        Doctor savedDoctor =
                doctorRepository.save(doctor);

        fhirIntegrationClient.createPractitioner(savedDoctor);
        
        return mapper.toResponse(savedDoctor);
    }

    private String generateDoctorNumber() {

        return "D" + System.currentTimeMillis();
    }

    @Override
    public DoctorResponse getDoctorById(Long id) {

        Doctor doctor = getDoctor(id);

        return mapper.toResponse(doctor);
    }

    @Override
    public Page<DoctorResponse> getAllDoctors(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort;

        if ("desc".equalsIgnoreCase(direction)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Doctor> doctors =
                doctorRepository.findByStatus(
                        DoctorStatus.ACTIVE,
                        pageable);

        return doctors.map(mapper::toResponse);
    }

    @Override
    public DoctorResponse updateDoctor(
            Long id,
            DoctorRequest request) {

        Doctor doctor = getDoctor(id);

        mapper.updateEntity(request, doctor);

        Doctor updatedDoctor =
                doctorRepository.save(doctor);

        return mapper.toResponse(updatedDoctor);
    }

    @Override
    public DoctorResponse updateDoctorPartially(
            Long id,
            DoctorPatchRequest request) {

        Doctor doctor = getDoctor(id);

        if (request.getFirstName() != null) {
            doctor.setFirstName(
                    request.getFirstName());
        }

        if (request.getLastName() != null) {
            doctor.setLastName(
                    request.getLastName());
        }

        if (request.getDateOfBirth() != null) {
            doctor.setDateOfBirth(
                    request.getDateOfBirth());
        }

        if (request.getGender() != null) {
            doctor.setGender(
                    request.getGender());
        }

        if (request.getSpecialization() != null) {
            doctor.setSpecialization(
                    request.getSpecialization());
        }

        if (request.getQualification() != null) {
            doctor.setQualification(
                    request.getQualification());
        }

        if (request.getLicenseNumber() != null) {
            doctor.setLicenseNumber(
                    request.getLicenseNumber());
        }

        if (request.getExperienceYears() != null) {
            doctor.setExperienceYears(
                    request.getExperienceYears());
        }

        if (request.getPhoneNumber() != null) {
            doctor.setPhoneNumber(
                    request.getPhoneNumber());
        }

        if (request.getEmail() != null) {
            doctor.setEmail(
                    request.getEmail());
        }

        if (request.getAddress() != null) {
            doctor.setAddress(
                    request.getAddress());
        }

        Doctor updatedDoctor =
                doctorRepository.save(doctor);

        return mapper.toResponse(updatedDoctor);
    }

    @Override
    public DoctorResponse updateDoctorStatus(
            Long id,
            DoctorStatusUpdateRequest request) {

        Doctor doctor = getDoctor(id);

        doctor.setStatus(request.getStatus());

        Doctor updatedDoctor =
                doctorRepository.save(doctor);

        return mapper.toResponse(updatedDoctor);
    }

    @Override
    public List<DoctorResponse> searchDoctors(
            String firstName,
            String lastName,
            String specialization,
            String qualification,
            String licenseNumber,
            String phoneNumber,
            String email) {

        List<Doctor> doctors;

        if (firstName != null
                && !firstName.isBlank()) {

            doctors = doctorRepository
                    .findByFirstNameContainingIgnoreCase(
                            firstName);

        } else if (lastName != null
                && !lastName.isBlank()) {

            doctors = doctorRepository
                    .findByLastNameContainingIgnoreCase(
                            lastName);

        } else if (specialization != null
                && !specialization.isBlank()) {

            doctors = doctorRepository
                    .findBySpecializationContainingIgnoreCase(
                            specialization);

        } else if (qualification != null
                && !qualification.isBlank()) {

            doctors = doctorRepository
                    .findByQualificationContainingIgnoreCase(
                            qualification);

        } else if (licenseNumber != null
                && !licenseNumber.isBlank()) {

            doctors = doctorRepository
                    .findByLicenseNumber(
                            licenseNumber);

        } else if (phoneNumber != null
                && !phoneNumber.isBlank()) {

            doctors = doctorRepository
                    .findByPhoneNumber(
                            phoneNumber);

        } else if (email != null
                && !email.isBlank()) {

            doctors = doctorRepository
                    .findByEmail(email);

        } else {
            return List.of();
        }

        return doctors.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public String deleteDoctor(Long id) {

        Doctor doctor = getDoctor(id);

        doctor.setStatus(DoctorStatus.INACTIVE);

        doctorRepository.save(doctor);

        return "Doctor deleted successfully";
    }

    private Doctor getDoctor(Long id) {

        return doctorRepository.findById(id)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: " + id));
    }
}