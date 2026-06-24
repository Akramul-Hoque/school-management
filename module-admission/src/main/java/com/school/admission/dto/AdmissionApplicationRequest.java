package com.school.admission.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdmissionApplicationRequest {
    @NotBlank
    private String applicantName;
    private String email;
    private String phone;
    private Long classAppliedId;
}
