package com.school.cms.service.api;

import com.school.cms.dto.NoticeDto;

import java.util.List;

public interface CmsContentService {
    List<NoticeDto> getPublishedNotices();
    NoticeDto getNoticeById(Long id);
    NoticeDto createNotice(NoticeDto noticeDto);
    NoticeDto updateNotice(Long id, NoticeDto noticeDto);
    void deleteNotice(Long id);

    // Pages
    PageDto getPageBySlug(String slug);
    PageDto createPage(PageDto dto);
    PageDto updatePage(Long id, PageDto dto);
    void deletePage(Long id);

    // News
    List<NewsDto> getPublishedNews();
    NewsDto getNewsById(Long id);
    NewsDto createNews(NewsDto dto);
    NewsDto updateNews(Long id, NewsDto dto);
    void deleteNews(Long id);

    // Contact submissions
    ContactSubmissionDto submitContact(ContactSubmissionRequest req, String ipAddress);
    List<ContactSubmissionDto> listContactSubmissions();
}
