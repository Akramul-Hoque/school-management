package com.school.cms.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagePublishedEvent {
    private Long pageId;
    private String slug;
    private String title;
}
