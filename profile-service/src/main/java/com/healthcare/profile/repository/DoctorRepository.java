package com.healthcare.profile.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.healthcare.profile.entity.Doctor;
import com.healthcare.profile.doctorDto.DoctorStatus;

public interface DoctorRepository  extends JpaRepository<Doctor, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByLicenseNumber(String licenseNumber);

    List<Doctor> findByFirstNameContainingIgnoreCase(
            String firstName);

    List<Doctor> findByLastNameContainingIgnoreCase(
            String lastName);

    List<Doctor> findBySpecializationContainingIgnoreCase(
            String specialization);

    List<Doctor> findByQualificationContainingIgnoreCase(
            String qualification);

    List<Doctor> findByLicenseNumber(
            String licenseNumber);

    List<Doctor> findByPhoneNumber(
            String phoneNumber);

    List<Doctor> findByEmail(
            String email);

    Page<Doctor> findByStatus(
            DoctorStatus status,
            Pageable pageable);
}
