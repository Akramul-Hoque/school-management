package com.school.hr.controller;

import com.school.common.model.ApiResponse;
import com.school.hr.dto.LeaveBalanceDto;
import com.school.hr.dto.LeaveRequestDto;
import com.school.hr.dto.LeaveRequestForm;
import com.school.hr.service.api.HrService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff/leave")
@RequiredArgsConstructor
public class LeaveController {

    private final HrService hrService;

    @PostMapping("/apply")
    public ApiResponse<LeaveRequestDto> applyLeave(@Valid @RequestBody LeaveRequestForm form) {
        return ApiResponse.success("Leave applied successfully", hrService.submitLeaveRequest(form));
    }

    @PatchMapping("/admin/hr/leave/{id}/approve")
    public ApiResponse<Void> approveLeave(@PathVariable Long id) {
        hrService.approveLeave(id);
        return ApiResponse.success("Leave approved successfully", null);
    }

    @GetMapping("/balance")
    public ApiResponse<LeaveBalanceDto> getLeaveBalance(@RequestParam Long staffId, @RequestParam String leaveType) {
        return ApiResponse.success("Fetched leave balance", hrService.getLeaveBalance(staffId, leaveType));
    }
}
