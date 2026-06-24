package com.school.cms.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class PageDto {
    private Long id;
    private String slug;
    private String title;
    private String content;
    private boolean published;
    private Instant createdAt;
}
