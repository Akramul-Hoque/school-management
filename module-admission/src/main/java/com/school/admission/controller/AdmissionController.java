package com.school.admission.controller;

import com.school.admission.dto.AdmissionApplicationDto;
import com.school.admission.service.api.AdmissionService;
import com.school.common.model.ApiResponse;
import com.school.common.model.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admission")
@RequiredArgsConstructor
public class AdmissionController {

    private final AdmissionService admissionService;

    @PostMapping("/apply")
    public ApiResponse<AdmissionApplicationDto> submitApplication(@RequestBody AdmissionApplicationDto dto) {
        return ApiResponse.success("Application submitted", admissionService.submitApplication(dto));
    }

    @GetMapping("/admin/applications/{id}")
    public ApiResponse<AdmissionApplicationDto> getApplication(@PathVariable Long id) {
        return ApiResponse.success("Fetched application", admissionService.getApplicationById(id));
    }

    @GetMapping("/admin/applications")
    public ApiResponse<PageResponse<AdmissionApplicationDto>> getApplications(
            @RequestParam(defaultValue = "PENDING") String status, Pageable pageable) {
        return ApiResponse.success("Fetched applications", PageResponse.from(admissionService.getApplicationsByStatus(status, pageable)));
    }

    @PostMapping("/admin/applications/{id}/approve")
    public ApiResponse<Void> approveApplication(@PathVariable Long id, @RequestParam Long sectionId) {
        admissionService.approveApplication(id, sectionId);
        return ApiResponse.success("Application approved and student created", null);
    }

    @PostMapping("/admin/applications/{id}/reject")
    public ApiResponse<Void> rejectApplication(@PathVariable Long id) {
        admissionService.rejectApplication(id);
        return ApiResponse.success("Application rejected", null);
    }
}
