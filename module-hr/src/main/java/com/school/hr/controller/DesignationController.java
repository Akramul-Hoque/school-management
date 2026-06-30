package com.school.hr.controller;

import com.school.common.model.ApiResponse;
import com.school.hr.dto.DesignationDto;
import com.school.hr.service.api.HrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/hr/designations")
@RequiredArgsConstructor
public class DesignationController {

    private final HrService hrService;

    @GetMapping
    public ApiResponse<List<DesignationDto>> getAllDesignations() {
        return ApiResponse.success("Fetched designations", hrService.getAllDesignations());
    }

    @PostMapping
    public ApiResponse<DesignationDto> createDesignation(@RequestBody DesignationDto dto) {
        return ApiResponse.success("Designation created", hrService.createDesignation(dto));
    }
}
