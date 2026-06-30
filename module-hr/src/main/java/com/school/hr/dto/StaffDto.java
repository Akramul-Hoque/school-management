package com.school.hr.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StaffDto {
    private Long id;
    private String staffCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfJoining;
    private boolean active;
    private Long departmentId;
    private String departmentName;
    private Long designationId;
    private String designationName;
}
