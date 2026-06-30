package com.school.hr.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApprovedEvent implements Serializable {
    private Long staffId;
    private Long leaveRequestId;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
}
