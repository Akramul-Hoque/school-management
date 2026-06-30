package com.school.hr.controller;

import com.school.common.model.ApiResponse;
import com.school.hr.dto.DepartmentDto;
import com.school.hr.service.api.HrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/hr/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final HrService hrService;

    @GetMapping
    public ApiResponse<List<DepartmentDto>> getAllDepartments() {
        return ApiResponse.success("Fetched departments", hrService.getAllDepartments());
    }

    @PostMapping
    public ApiResponse<DepartmentDto> createDepartment(@RequestBody DepartmentDto dto) {
        return ApiResponse.success("Department created", hrService.createDepartment(dto));
    }
}
