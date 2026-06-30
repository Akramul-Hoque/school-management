package com.school.student.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StudentDto {
    private Long id;
    private String studentCode;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private LocalDate admissionDate;
    private String gender;
    private boolean active;
    private Long currentClassId;
    private Long currentSectionId;
    private Long currentSessionId;
    private GuardianDto guardian;
}
