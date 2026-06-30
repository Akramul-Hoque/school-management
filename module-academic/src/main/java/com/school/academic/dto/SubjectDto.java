package com.school.academic.dto;

import lombok.Data;

@Data
public class SubjectDto {
    private Long id;
    private String name;
    private String code;
    private boolean elective;
}
