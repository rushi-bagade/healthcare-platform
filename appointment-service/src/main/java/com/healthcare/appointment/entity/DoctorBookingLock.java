package com.healthcare.appointment.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity 
@Table(
    name = "doctor_booking_locks",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctor_id", "lock_date"})
    }
)
@Data 
public class DoctorBookingLock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "lock_date", nullable = false)
    private LocalDate lockDate;
}