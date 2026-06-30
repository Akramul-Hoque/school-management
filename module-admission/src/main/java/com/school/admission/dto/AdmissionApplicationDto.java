package com.school.admission.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AdmissionApplicationDto {
    private Long id;
    private String applicationNumber;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private Long appliedClassId;
    private Long appliedSessionId;
    
    // Guardian details
    private String guardianFirstName;
    private String guardianLastName;
    private String guardianEmail;
    private String guardianPhone;
    private String guardianRelation;
    
    private String status;
}
