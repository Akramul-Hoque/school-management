package com.school.hr.controller;

import com.school.common.model.ApiResponse;
import com.school.hr.dto.StaffDto;
import com.school.hr.service.api.HrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/hr/staff")
@RequiredArgsConstructor
public class StaffController {

    private final HrService hrService;

    @GetMapping("/{id}")
    public ApiResponse<StaffDto> getStaffById(@PathVariable Long id) {
        return ApiResponse.success("Fetched staff successfully", hrService.getStaffById(id));
    }

    @GetMapping("/active-teachers")
    public ApiResponse<List<StaffDto>> getActiveTeachers() {
        return ApiResponse.success("Fetched active teachers", hrService.getActiveTeachers());
    }

    @PostMapping("/onboard")
    public ApiResponse<StaffDto> onboardStaff(@RequestBody StaffDto staffDto) {
        return ApiResponse.success("Staff onboarded successfully", hrService.onboardStaff(staffDto));
    }
}
