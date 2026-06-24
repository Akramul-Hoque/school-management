package com.school.cms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactSubmissionRequest {
    @NotBlank
    private String name;
    private String email;
    private String phone;
    @NotBlank
    private String message;
}
