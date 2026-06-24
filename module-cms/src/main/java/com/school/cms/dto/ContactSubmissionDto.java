package com.school.cms.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class ContactSubmissionDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String message;
    private Instant createdAt;
}
