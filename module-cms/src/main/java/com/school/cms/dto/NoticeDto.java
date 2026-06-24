package com.school.cms.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class NoticeDto {
    private Long id;
    private String title;
    private String content;
    private boolean published;
    private Instant createdAt;
}
