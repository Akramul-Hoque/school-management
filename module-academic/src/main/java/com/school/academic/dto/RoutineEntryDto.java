package com.school.academic.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class RoutineEntryDto {
    private Long id;
    private Long sessionId;
    private Long classId;
    private String className;
    private Long sectionId;
    private String sectionName;
    private Long subjectId;
    private String subjectName;
    private Long staffId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
