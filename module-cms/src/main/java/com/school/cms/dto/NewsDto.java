package com.school.cms.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class NewsDto {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private boolean published;
    private Instant publishedAt;
}
