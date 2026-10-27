package com.healthcare.profile.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.healthcare.profile.entity.Patient;
import com.healthcare.profile.dto.PatientStatus;

public interface PatientRepository extends  JpaRepository<Patient, Long>{

     boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    List<Patient> findByFirstNameContainingIgnoreCase(
            String firstName);

    List<Patient> findByLastNameContainingIgnoreCase(
            String lastName);

    List<Patient> findByPhoneNumber(String phoneNumber);

    List<Patient> findByEmail(String email);

    Page<Patient> findByStatus(
           PatientStatus  status,
            Pageable pageable);
}
