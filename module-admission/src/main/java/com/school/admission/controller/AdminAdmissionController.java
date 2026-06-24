package com.school.admission.controller;

import com.school.admission.dto.AdmissionApplicationDto;
import com.school.admission.dto.AdmissionApplicationRequest;
import com.school.admission.service.api.AdmissionService;
import com.school.common.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/admissions")
@RequiredArgsConstructor
public class AdminAdmissionController {

    private final AdmissionService admissionService;

    @PostMapping
    public ApiResponse<AdmissionApplicationDto> submit(@Valid @RequestBody AdmissionApplicationRequest req) {
        AdmissionApplicationDto dto = admissionService.submit(req);
        return ApiResponse.success("Application submitted", dto);
    }

    @GetMapping("/{id}")
    public ApiResponse<AdmissionApplicationDto> getById(@PathVariable Long id) {
        AdmissionApplicationDto dto = admissionService.getById(id);
        return ApiResponse.success("Fetched application", dto);
    }

    @PatchMapping("/{id}/approve")
    public ApiResponse<Long> approve(@PathVariable Long id) {
        Long studentId = admissionService.approveAndConvert(id);
        return ApiResponse.success("Application approved and student created", studentId);
    }

    @PatchMapping("/{id}/reject")
    public ApiResponse<Void> reject(@PathVariable Long id) {
        admissionService.reject(id);
        return ApiResponse.success("Application rejected", null);
    }
}
