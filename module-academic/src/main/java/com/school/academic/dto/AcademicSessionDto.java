package com.school.academic.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AcademicSessionDto {
    private Long id;
    private String year;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean current;
}
