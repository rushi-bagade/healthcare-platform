package com.healthcare.appointment.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.healthcare.appointment.entity.Appointment;
import com.healthcare.appointment.entity.AppointmentStatus;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment findByDoctorId(Long doctorId);

    @Query("""
            SELECT COUNT(a) > 0
            FROM Appointment a
            WHERE a.doctorId = :doctorId
              AND a.appointmentDate = :appointmentDate
              AND a.status IN :statuses
              AND a.startTime < :endTime
              AND a.endTime > :startTime
            """)
          boolean existsOverlappingAppointment(
            @Param("doctorId") Long doctorId,
            @Param("appointmentDate") LocalDate appointmentDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("statuses") List<AppointmentStatus> statuses);


        boolean existsByAppointmentNumber(String appointmentNumber);

        Page<Appointment> findByPatientId(
            Long patientId,
            Pageable pageable);

        Page<Appointment> findByDoctorId(
            Long doctorId,
            Pageable pageable);


    @Query("""
    SELECT COUNT(a) > 0
    FROM Appointment a
    WHERE a.doctorId = :doctorId
      AND a.appointmentDate = :appointmentDate
      AND a.id <> :appointmentId
      AND a.status IN :statuses
      AND a.startTime < :endTime
      AND a.endTime > :startTime
    """)
boolean existsOverlappingAppointmentForReschedule(
        @Param("appointmentId") Long appointmentId,
        @Param("doctorId") Long doctorId,
        @Param("appointmentDate") LocalDate appointmentDate,
        @Param("startTime") LocalTime startTime,
        @Param("endTime") LocalTime endTime,
        @Param("statuses") List<AppointmentStatus> statuses);        
}
