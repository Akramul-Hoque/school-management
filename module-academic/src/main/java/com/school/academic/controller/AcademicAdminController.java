package com.school.academic.controller;

import com.school.academic.dto.*;
import com.school.academic.service.api.AcademicService;
import com.school.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/academic")
@RequiredArgsConstructor
public class AcademicAdminController {

    private final AcademicService academicService;

    @GetMapping("/sessions")
    public ApiResponse<List<AcademicSessionDto>> getAllSessions() {
        return ApiResponse.success("Fetched all sessions", academicService.getAllSessions());
    }

    @PostMapping("/sessions")
    public ApiResponse<AcademicSessionDto> createSession(@RequestBody AcademicSessionDto dto) {
        return ApiResponse.success("Created session", academicService.createSession(dto));
    }

    @GetMapping("/classes")
    public ApiResponse<List<ClassDto>> getAllClasses() {
        return ApiResponse.success("Fetched all classes", academicService.getAllClasses());
    }

    @PostMapping("/classes")
    public ApiResponse<ClassDto> createClass(@RequestBody ClassDto dto) {
        return ApiResponse.success("Created class", academicService.createClass(dto));
    }

    @GetMapping("/sections")
    public ApiResponse<List<SectionDto>> getAllSections() {
        return ApiResponse.success("Fetched all sections", academicService.getAllSections());
    }

    @PostMapping("/sections")
    public ApiResponse<SectionDto> createSection(@RequestBody SectionDto dto) {
        return ApiResponse.success("Created section", academicService.createSection(dto));
    }

    @GetMapping("/subjects")
    public ApiResponse<List<SubjectDto>> getAllSubjects() {
        return ApiResponse.success("Fetched all subjects", academicService.getAllSubjects());
    }

    @PostMapping("/subjects")
    public ApiResponse<SubjectDto> createSubject(@RequestBody SubjectDto dto) {
        return ApiResponse.success("Created subject", academicService.createSubject(dto));
    }

    @PostMapping("/teacher-mapping")
    public ApiResponse<TeacherAssignmentDto> mapTeacher(@RequestBody TeacherAssignmentDto dto) {
        return ApiResponse.success("Mapped teacher successfully", academicService.mapTeacherToClass(dto));
    }

    @PostMapping("/routine")
    public ApiResponse<RoutineEntryDto> createRoutine(@RequestBody RoutineEntryDto dto) {
        return ApiResponse.success("Created routine entry", academicService.createRoutineEntry(dto));
    }

    @GetMapping("/routine")
    public ApiResponse<List<RoutineEntryDto>> getRoutine(@RequestParam Long classId, @RequestParam Long sectionId, @RequestParam Long sessionId) {
        return ApiResponse.success("Fetched routine", academicService.getRoutineForClass(classId, sectionId, sessionId));
    }
}
