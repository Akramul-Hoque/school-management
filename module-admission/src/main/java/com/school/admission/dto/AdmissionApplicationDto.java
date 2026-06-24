package com.school.admission.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class AdmissionApplicationDto {
    private Long id;
    private String applicantName;
    private String email;
    private String phone;
    private Long classAppliedId;
    private String status;
    private Instant createdAt;
}
