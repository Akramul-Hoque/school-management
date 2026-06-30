package com.school.student.service.api;

import com.school.student.dto.StudentDto;
import com.school.student.dto.StudentSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    // Note: AdmissionApplicationDto is in the admission module. 
    // To decouple compile-time, we can either pass a generic Object/DTO or use basic parameters.
    // For simplicity, we will pass StudentDto which will be mapped from AdmissionApplicationDto by the AdmissionService.
    StudentDto createStudentFromApplication(StudentDto studentDto);

    StudentDto getStudentById(Long id);

    Page<StudentDto> search(StudentSearchFilter filter, Pageable pageable);

    void promoteStudents(Long fromSessionId, Long toSessionId);

    byte[] generateTransferCertificate(Long studentId);
}
