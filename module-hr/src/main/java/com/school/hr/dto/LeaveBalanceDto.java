package com.school.hr.dto;

import lombok.Data;

@Data
public class LeaveBalanceDto {
    private Long id;
    private Long staffId;
    private String leaveType;
    private int totalDays;
    private int usedDays;
}
