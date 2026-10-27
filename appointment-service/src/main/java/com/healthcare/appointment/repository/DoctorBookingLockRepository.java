package com.healthcare.appointment.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.healthcare.appointment.entity.DoctorBookingLock;
import jakarta.persistence.LockModeType;

public interface DoctorBookingLockRepository  extends JpaRepository<DoctorBookingLock, Long> {

    @Modifying
    @Query(value = """
        INSERT INTO doctor_booking_locks (doctor_id, lock_date)
        VALUES (:doctorId, :lockDate)
        ON DUPLICATE KEY UPDATE id = id  
        """, nativeQuery = true) //Insert the row. But if that row already exists because of a duplicate unique key, don't fail—just keep the existing row unchanged
    void ensureLockRow(
            @Param("doctorId") Long doctorId,
            @Param("lockDate") LocalDate lockDate);


    @Lock(LockModeType.PESSIMISTIC_WRITE)  //When I get this row, lock it so another transaction cannot modify/acquire it until my transaction finishes
    @Query("""
        SELECT l
        FROM DoctorBookingLock l
        WHERE l.doctorId = :doctorId
          AND l.lockDate = :lockDate
        """)
    Optional<DoctorBookingLock> findForUpdate(
            @Param("doctorId") Long doctorId,
            @Param("lockDate") LocalDate lockDate);

    //"pessimistic” means we assume a conflict might happen, so we lock the resource in advance.        
}
