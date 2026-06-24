package com.school.cms.controller;

import com.school.cms.dto.NoticeDto;
import com.school.cms.service.api.CmsContentService;
import com.school.common.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cms")
@RequiredArgsConstructor
public class AdminCmsController {

    private final CmsContentService cmsContentService;

    @GetMapping("/notices")
    public ApiResponse<List<NoticeDto>> listNotices() {
        return ApiResponse.success("Notices list", cmsContentService.getPublishedNotices());
    }

    @PostMapping("/notices")
    public ApiResponse<NoticeDto> createNotice(@Valid @RequestBody NoticeDto dto) {
        NoticeDto created = cmsContentService.createNotice(dto);
        return ApiResponse.success("Notice created", created);
    }

    @PutMapping("/notices/{id}")
    public ApiResponse<NoticeDto> updateNotice(@PathVariable Long id, @Valid @RequestBody NoticeDto dto) {
        NoticeDto updated = cmsContentService.updateNotice(id, dto);
        return ApiResponse.success("Notice updated", updated);
    }

    @DeleteMapping("/notices/{id}")
    public ApiResponse<Void> deleteNotice(@PathVariable Long id) {
        cmsContentService.deleteNotice(id);
        return ApiResponse.success("Notice deleted", null);
    }

    // Pages
    @GetMapping("/pages")
    public ApiResponse<List<com.school.cms.dto.PageDto>> listPages() {
        // For admin, we could fetch all pages; reuse public cache isn't suitable — keep simple
        return ApiResponse.success("Pages list", List.of());
    }

    @PostMapping("/pages")
    public ApiResponse<com.school.cms.dto.PageDto> createPage(@Valid @RequestBody com.school.cms.dto.PageDto dto) {
        return ApiResponse.success("Page created", cmsContentService.createPage(dto));
    }

    @PutMapping("/pages/{id}")
    public ApiResponse<com.school.cms.dto.PageDto> updatePage(@PathVariable Long id, @Valid @RequestBody com.school.cms.dto.PageDto dto) {
        return ApiResponse.success("Page updated", cmsContentService.updatePage(id, dto));
    }

    @DeleteMapping("/pages/{id}")
    public ApiResponse<Void> deletePage(@PathVariable Long id) {
        cmsContentService.deletePage(id);
        return ApiResponse.success("Page deleted", null);
    }

    // News
    @GetMapping("/news")
    public ApiResponse<List<com.school.cms.dto.NewsDto>> listNews() {
        return ApiResponse.success("News list", cmsContentService.getPublishedNews());
    }

    @PostMapping("/news")
    public ApiResponse<com.school.cms.dto.NewsDto> createNews(@Valid @RequestBody com.school.cms.dto.NewsDto dto) {
        return ApiResponse.success("News created", cmsContentService.createNews(dto));
    }

    @PutMapping("/news/{id}")
    public ApiResponse<com.school.cms.dto.NewsDto> updateNews(@PathVariable Long id, @Valid @RequestBody com.school.cms.dto.NewsDto dto) {
        return ApiResponse.success("News updated", cmsContentService.updateNews(id, dto));
    }

    @DeleteMapping("/news/{id}")
    public ApiResponse<Void> deleteNews(@PathVariable Long id) {
        cmsContentService.deleteNews(id);
        return ApiResponse.success("News deleted", null);
    }

    // Contact submissions
    @GetMapping("/contact-submissions")
    public ApiResponse<List<com.school.cms.dto.ContactSubmissionDto>> listContacts() {
        return ApiResponse.success("Contact submissions", cmsContentService.listContactSubmissions());
    }
}
