package com.school.student.dto;

import lombok.Data;

@Data
public class GuardianDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String relationToStudent;
}
