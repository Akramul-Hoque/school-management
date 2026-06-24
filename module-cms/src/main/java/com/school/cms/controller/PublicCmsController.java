package com.school.cms.controller;

import com.school.cms.dto.NoticeDto;
import com.school.cms.service.api.CmsContentService;
import com.school.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicCmsController {

    private final CmsContentService cmsContentService;

    @GetMapping("/notices")
    public ApiResponse<List<NoticeDto>> getPublishedNotices() {
        return ApiResponse.success("Published notices", cmsContentService.getPublishedNotices());
    }

    @GetMapping("/pages/{slug}")
    public ApiResponse<com.school.cms.dto.PageDto> getPageBySlug(@org.springframework.web.bind.annotation.PathVariable String slug) {
        return ApiResponse.success("Page retrieved", cmsContentService.getPageBySlug(slug));
    }

    @GetMapping("/news")
    public ApiResponse<List<com.school.cms.dto.NewsDto>> getPublishedNews() {
        return ApiResponse.success("Published news", cmsContentService.getPublishedNews());
    }
}
