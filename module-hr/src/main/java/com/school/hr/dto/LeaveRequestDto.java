package com.school.hr.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveRequestDto {
    private Long id;
    private Long staffId;
    private String staffName;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;
}
