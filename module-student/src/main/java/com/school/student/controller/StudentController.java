package com.school.student.controller;

import com.school.common.model.ApiResponse;
import com.school.common.model.PageResponse;
import com.school.student.dto.StudentDto;
import com.school.student.dto.StudentSearchFilter;
import com.school.student.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public ApiResponse<StudentDto> getStudentById(@PathVariable Long id) {
        return ApiResponse.success("Fetched student", studentService.getStudentById(id));
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<StudentDto>> searchStudents(
            @ModelAttribute StudentSearchFilter filter, Pageable pageable) {
        Page<StudentDto> result = studentService.search(filter, pageable);
        return ApiResponse.success("Search results", PageResponse.from(result));
    }

    @PostMapping("/promote")
    public ApiResponse<Void> promoteStudents(@RequestParam Long fromSessionId, @RequestParam Long toSessionId) {
        studentService.promoteStudents(fromSessionId, toSessionId);
        return ApiResponse.success("Students promoted", null);
    }

    @GetMapping("/{id}/transfer-certificate")
    public ApiResponse<byte[]> getTransferCertificate(@PathVariable Long id) {
        byte[] pdf = studentService.generateTransferCertificate(id);
        return ApiResponse.success("Transfer Certificate generated", pdf);
    }
}
