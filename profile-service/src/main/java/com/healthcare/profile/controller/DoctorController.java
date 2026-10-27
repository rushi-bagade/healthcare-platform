package com.healthcare.profile.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.profile.doctorDto.DoctorPatchRequest;
import com.healthcare.profile.doctorDto.DoctorRequest;
import com.healthcare.profile.doctorDto.DoctorResponse;
import com.healthcare.profile.doctorDto.DoctorStatusUpdateRequest;
import com.healthcare.profile.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/profiles/doctors")
public class DoctorController {

        private final DoctorService doctorService;

        public DoctorController(DoctorService doctorService) {
                this.doctorService = doctorService;
        }

        @PostMapping
        public ResponseEntity<DoctorResponse> createDoctor(
                        @Valid @RequestBody DoctorRequest request) {

                DoctorResponse response = doctorService.createDoctor(request);

                return new ResponseEntity<>(
                                response,
                                HttpStatus.CREATED);
        }

        // added a comment
        @GetMapping
        public ResponseEntity<Page<DoctorResponse>> getAllDoctors(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "asc") String direction) {

                Page<DoctorResponse> response = doctorService.getAllDoctors(
                                page,
                                size,
                                sortBy,
                                direction);

                return ResponseEntity.ok(response);
        }

        @GetMapping("/search")
        public ResponseEntity<List<DoctorResponse>> searchDoctors(
                        @RequestParam(required = false) String firstName,
                        @RequestParam(required = false) String lastName,
                        @RequestParam(required = false) String specialization,
                        @RequestParam(required = false) String qualification,
                        @RequestParam(required = false) String licenseNumber,
                        @RequestParam(required = false) String phoneNumber,
                        @RequestParam(required = false) String email) {

                List<DoctorResponse> doctors = doctorService.searchDoctors(
                                firstName,
                                lastName,
                                specialization,
                                qualification,
                                licenseNumber,
                                phoneNumber,
                                email);

                return ResponseEntity.ok(doctors);
        }

        @GetMapping("/{id}")
        public ResponseEntity<DoctorResponse> getDoctorById(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                doctorService.getDoctorById(id));
        }

        @PutMapping("/{id}")
        public ResponseEntity<DoctorResponse> updateDoctor(
                        @PathVariable Long id,
                        @Valid @RequestBody DoctorRequest request) {

                return ResponseEntity.ok(
                                doctorService.updateDoctor(id, request));
        }

        @PatchMapping("/{id}")
        public ResponseEntity<DoctorResponse> updateDoctorPartially(
                        @PathVariable Long id,
                        @Valid @RequestBody DoctorPatchRequest request) {

                return ResponseEntity.ok(
                                doctorService.updateDoctorPartially(
                                                id, request));
        }

        @PatchMapping("/{id}/status")
        public ResponseEntity<DoctorResponse> updateDoctorStatus(
                        @PathVariable Long id,
                        @Valid @RequestBody DoctorStatusUpdateRequest request) {

                return ResponseEntity.ok(
                                doctorService.updateDoctorStatus(
                                                id, request));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteDoctor(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                doctorService.deleteDoctor(id));
        }
}