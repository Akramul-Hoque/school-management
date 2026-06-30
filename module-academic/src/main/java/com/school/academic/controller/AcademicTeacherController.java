package com.school.academic.controller;

import com.school.academic.dto.TeacherAssignmentDto;
import com.school.academic.service.api.AcademicService;
import com.school.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/academic")
@RequiredArgsConstructor
public class AcademicTeacherController {

    private final AcademicService academicService;

    @GetMapping("/my-assignments")
    public ApiResponse<List<TeacherAssignmentDto>> getMyAssignments(@RequestParam Long staffId) {
        return ApiResponse.success("Fetched assignments", academicService.getAssignmentsForTeacher(staffId));
    }
}
