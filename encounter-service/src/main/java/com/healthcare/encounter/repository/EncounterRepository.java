package com.healthcare.encounter.repository;

import com.healthcare.encounter.entity.Encounter;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {

    boolean existsByAppointmentId(Long appointmentId);

    Optional<Encounter> findByAppointmentId(Long appointmentId);

    Page<Encounter> findByPatientId(Long patientId, Pageable pageable);

    Page<Encounter> findByDoctorId(Long doctorId, Pageable pageable);
}
