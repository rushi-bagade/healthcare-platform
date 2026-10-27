package com.healthcare.appointment.service;

import java.time.LocalTime;
import java.util.List;
import com.healthcare.appointment.exception.AppointmentNotFoundException;
import com.healthcare.appointment.dto.AppointmentStatusUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.healthcare.appointment.dto.AppointmentResponse;
import com.healthcare.appointment.client.ProfileServiceClient;
import com.healthcare.appointment.dto.AppointmentFhirRequest;
import com.healthcare.appointment.dto.AppointmentRequest;
import com.healthcare.appointment.entity.Appointment;
import com.healthcare.appointment.entity.AppointmentStatus;
import com.healthcare.appointment.exception.AppointmentSlotNotAvailableException;
import com.healthcare.appointment.exception.DoctorNotFoundException;
import com.healthcare.appointment.exception.PatientNotFoundException;
import com.healthcare.appointment.mapper.AppointmentMapper;
import com.healthcare.appointment.repository.AppointmentRepository;
import com.healthcare.appointment.repository.DoctorBookingLockRepository;
import com.healthcare.appointment.client.FhirIntegrationClient;

import jakarta.transaction.Transactional;

import com.healthcare.appointment.dto.RescheduleAppointmentRequest;

@Service
public class AppointmentServiceImpl implements AppointmentService {

        private AppointmentRepository repo;
        private AppointmentMapper mapper;
        private final ProfileServiceClient profileServiceClient;
        private final DoctorBookingLockRepository lockRepository;
        private final FhirIntegrationClient fhirIntegrationClient;

        public AppointmentServiceImpl(AppointmentRepository repo, AppointmentMapper mapper,
                        ProfileServiceClient profileServiceClient, DoctorBookingLockRepository lockRepository,
                        FhirIntegrationClient fhirIntegrationClient) {
                this.repo = repo;
                this.mapper = mapper;
                this.profileServiceClient = profileServiceClient;
                this.lockRepository = lockRepository;
                this.fhirIntegrationClient = fhirIntegrationClient;
        }

        @Override
        @Transactional // @Transactional keeps the pessimistic lock alive while you check availability
                       // and save the appointment, and releases the lock when the transaction commits
                       // or rolls back.
        public String createAppointment(AppointmentRequest request) {

                validateTime(request.getStartTime(), request.getEndTime());

                boolean patientExists = profileServiceClient.patientExists(request.getPatientId());

                // Validate patient
                if (!patientExists) {
                        throw new PatientNotFoundException(
                                        "Patient not found with id: "
                                                        + request.getPatientId());
                }

                // Validate doctor
                boolean doctorExists = profileServiceClient.doctorExists(
                                request.getDoctorId());

                if (!doctorExists) {
                        throw new DoctorNotFoundException(
                                        "Doctor not found with id: "
                                                        + request.getDoctorId());
                }

                // Make sure lock row exists
                lockRepository.ensureLockRow(
                                request.getDoctorId(),
                                request.getAppointmentDate());

                // Acquire database lock
                lockRepository.findForUpdate(
                                request.getDoctorId(),
                                request.getAppointmentDate())
                                .orElseThrow(() -> new IllegalStateException("Unable to acquire booking lock"));
                // Once findForUpdate() acquires the database lock, the current transaction
                // continues executing the next lines. Another transaction trying to acquire the
                // same lock must wait.

                // Now check availability
                checkAvailability(request);

                Appointment savedAppointment = mapper.toEntity(request);
                savedAppointment.setAppointmentNumber(generateAppointmentNumber());
                savedAppointment.setStatus(AppointmentStatus.SCHEDULED);
                repo.save(savedAppointment);

                AppointmentFhirRequest fhirRequest = new AppointmentFhirRequest(
                                savedAppointment.getId(),
                                savedAppointment.getPatientId(),
                                savedAppointment.getDoctorId(),
                                savedAppointment.getAppointmentDate(),
                                savedAppointment.getStartTime(),
                                savedAppointment.getEndTime(),
                                savedAppointment.getStatus().name(),
                                savedAppointment.getReason());

                fhirIntegrationClient.createAppointment(fhirRequest);
                return "Appointment Created Successfully..!";
        }

        private void checkAvailability(AppointmentRequest request) {

                List<AppointmentStatus> blockingStatuses = List.of(
                                AppointmentStatus.SCHEDULED,
                                AppointmentStatus.CONFIRMED,
                                AppointmentStatus.RESCHEDULED);

                boolean slotAlreadyBooked = repo.existsOverlappingAppointment(
                                request.getDoctorId(),
                                request.getAppointmentDate(),
                                request.getStartTime(),
                                request.getEndTime(),
                                blockingStatuses);

                if (slotAlreadyBooked) {

                        throw new AppointmentSlotNotAvailableException(
                                        "Doctor is already booked for the requested time slot");
                }
        }

        private void validateTime(LocalTime startTime, LocalTime endTime) {

                if (!startTime.isBefore(endTime)) {
                        throw new IllegalArgumentException(
                                        "End time must be after start time");
                }
        }

        private String generateAppointmentNumber() {
                return "A" + System.currentTimeMillis();
        }

        @Override
        public AppointmentResponse getAppointmentById(Long id) {

                Appointment appointment = repo.findById(id)
                                .orElseThrow(() -> new AppointmentNotFoundException(
                                                "Appointment not found with id: " + id));

                return mapper.toResponse(appointment);
        }

        @Override
        public Page<AppointmentResponse> getAllAppointments(
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

                Pageable pageable = PageRequest.of(page, size, sort);

                Page<Appointment> appointments = repo.findAll(pageable);

                return appointments.map(mapper::toResponse);
        }

        @Override
        public Page<AppointmentResponse> getAppointmentsByPatient(
                        Long patientId,
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

                Pageable pageable = PageRequest.of(page, size, sort);

                Page<Appointment> appointments = repo.findByPatientId(
                                patientId,
                                pageable);

                return appointments.map(mapper::toResponse);
        }

        @Override
        public Page<AppointmentResponse> getAppointmentsByDoctor(
                        Long doctorId,
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

                Pageable pageable = PageRequest.of(page, size, sort);

                Page<Appointment> appointments = repo.findByDoctorId(
                                doctorId,
                                pageable);

                return appointments.map(mapper::toResponse);
        }

        @Override
        public AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatusUpdateRequest request) {

                Appointment appointment = repo.findById(id)
                                .orElseThrow(() -> new AppointmentNotFoundException(
                                                "Appointment not found with id: " + id));

                appointment.setStatus(request.getStatus());

                Appointment updatedAppointment = repo.save(appointment);

                return mapper.toResponse(updatedAppointment);
        }

        @Override
        public AppointmentResponse cancelAppointment(Long id) {

                Appointment appointment = repo.findById(id)
                                .orElseThrow(() -> new AppointmentNotFoundException(
                                                "Appointment not found with id: " + id));

                if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
                        throw new IllegalStateException(
                                        "Appointment is already cancelled");
                }

                if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
                        throw new IllegalStateException(
                                        "Completed appointment cannot be cancelled");
                }

                appointment.setStatus(AppointmentStatus.CANCELLED);

                Appointment updatedAppointment = repo.save(appointment);

                return mapper.toResponse(updatedAppointment);
        }

        @Override
        public AppointmentResponse rescheduleAppointment(
                        Long id,
                        RescheduleAppointmentRequest request) {

                Appointment appointment = repo.findById(id)
                                .orElseThrow(() -> new AppointmentNotFoundException(
                                                "Appointment not found with id: " + id));

                if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
                        throw new IllegalStateException(
                                        "Cancelled appointment cannot be rescheduled");
                }

                if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
                        throw new IllegalStateException(
                                        "Completed appointment cannot be rescheduled");
                }

                validateTime(
                                request.getStartTime(),
                                request.getEndTime());

                List<AppointmentStatus> blockingStatuses = List.of(
                                AppointmentStatus.SCHEDULED,
                                AppointmentStatus.CONFIRMED,
                                AppointmentStatus.RESCHEDULED);

                boolean slotAlreadyBooked = repo.existsOverlappingAppointmentForReschedule(
                                appointment.getId(),
                                appointment.getDoctorId(),
                                request.getAppointmentDate(),
                                request.getStartTime(),
                                request.getEndTime(),
                                blockingStatuses);

                if (slotAlreadyBooked) {
                        throw new AppointmentSlotNotAvailableException(
                                        "Doctor is already booked for the requested time slot");
                }

                appointment.setAppointmentDate(
                                request.getAppointmentDate());

                appointment.setStartTime(
                                request.getStartTime());

                appointment.setEndTime(
                                request.getEndTime());

                appointment.setStatus(
                                AppointmentStatus.RESCHEDULED);

                Appointment updatedAppointment = repo.save(appointment);

                return mapper.toResponse(updatedAppointment);
        }
}
