package com.school.student.dto;

import lombok.Data;

@Data
public class StudentSearchFilter {
    private Long classId;
    private Long sectionId;
    private String name;
    private String studentCode;
}
