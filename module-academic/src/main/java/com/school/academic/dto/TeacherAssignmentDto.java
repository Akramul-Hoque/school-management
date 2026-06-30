package com.school.academic.dto;

import lombok.Data;

@Data
public class TeacherAssignmentDto {
    private Long id;
    private Long staffId;
    private Long classId;
    private String className;
    private Long sectionId;
    private String sectionName;
    private Long subjectId;
    private String subjectName;
    private Long sessionId;
}
