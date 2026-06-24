package com.school.cms.service.impl;

import com.school.cms.dto.*;
import com.school.cms.entity.*;
import com.school.cms.event.*;
import com.school.cms.repository.*;
import com.school.cms.service.api.CmsContentService;
import com.school.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CmsContentServiceImpl implements CmsContentService {

    private final NoticeRepository noticeRepository;
    private final PageRepository pageRepository;
    private final NewsItemRepository newsRepository;
    private final ContactSubmissionRepository contactRepository;
    private final ApplicationEventPublisher eventPublisher;

    // Notices
    @Override
    @Cacheable(value = "publishedNotices")
    public List<NoticeDto> getPublishedNotices() {
        return noticeRepository.findByPublishedTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeDto getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notice not found: " + id));
        return toDto(notice);
    }

    @Override
    @Transactional
    @CacheEvict(value = "publishedNotices", allEntries = true)
    public NoticeDto createNotice(NoticeDto noticeDto) {
        Notice notice = new Notice();
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setPublished(noticeDto.isPublished());
        Notice saved = noticeRepository.save(notice);

        if (saved.isPublished()) {
            eventPublisher.publishEvent(new NoticePublishedEvent(saved.getId(), saved.getTitle()));
        }
        return toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "publishedNotices", allEntries = true)
    public NoticeDto updateNotice(Long id, NoticeDto noticeDto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notice not found: " + id));
        boolean wasPublished = notice.isPublished();
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setPublished(noticeDto.isPublished());
        Notice saved = noticeRepository.save(notice);

        if (!wasPublished && saved.isPublished()) {
            eventPublisher.publishEvent(new NoticePublishedEvent(saved.getId(), saved.getTitle()));
        }
        return toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "publishedNotices", allEntries = true)
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notice not found: " + id));
        noticeRepository.delete(notice);
    }

    private NoticeDto toDto(Notice n) {
        NoticeDto d = new NoticeDto();
        d.setId(n.getId());
        d.setTitle(n.getTitle());
        d.setContent(n.getContent());
        d.setPublished(n.isPublished());
        d.setCreatedAt(n.getCreatedAt());
        return d;
    }

    // Pages
    @Override
    @Cacheable(value = "pages", key = "#slug")
    public PageDto getPageBySlug(String slug) {
        Page p = pageRepository.findBySlugAndPublishedTrue(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Page not found: " + slug));
        return toPageDto(p);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pages", allEntries = true)
    public PageDto createPage(PageDto dto) {
        Page p = new Page();
        p.setSlug(dto.getSlug());
        p.setTitle(dto.getTitle());
        p.setContent(dto.getContent());
        p.setPublished(dto.isPublished());
        Page saved = pageRepository.save(p);
        if (saved.isPublished()) {
            eventPublisher.publishEvent(new PagePublishedEvent(saved.getId(), saved.getSlug(), saved.getTitle()));
        }
        return toPageDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pages", allEntries = true)
    public PageDto updatePage(Long id, PageDto dto) {
        Page p = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page not found: " + id));
        boolean wasPublished = p.isPublished();
        p.setSlug(dto.getSlug());
        p.setTitle(dto.getTitle());
        p.setContent(dto.getContent());
        p.setPublished(dto.isPublished());
        Page saved = pageRepository.save(p);
        if (!wasPublished && saved.isPublished()) {
            eventPublisher.publishEvent(new PagePublishedEvent(saved.getId(), saved.getSlug(), saved.getTitle()));
        }
        return toPageDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pages", allEntries = true)
    public void deletePage(Long id) {
        Page p = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page not found: " + id));
        pageRepository.delete(p);
    }

    private PageDto toPageDto(Page p) {
        PageDto d = new PageDto();
        d.setId(p.getId());
        d.setSlug(p.getSlug());
        d.setTitle(p.getTitle());
        d.setContent(p.getContent());
        d.setPublished(p.isPublished());
        d.setCreatedAt(p.getCreatedAt());
        return d;
    }

    // News
    @Override
    @Cacheable(value = "publishedNews")
    public List<NewsDto> getPublishedNews() {
        return newsRepository.findByPublishedTrueOrderByPublishedAtDesc()
                .stream()
                .map(this::toNewsDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDto getNewsById(Long id) {
        NewsItem n = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found: " + id));
        return toNewsDto(n);
    }

    @Override
    @Transactional
    @CacheEvict(value = "publishedNews", allEntries = true)
    public NewsDto createNews(NewsDto dto) {
        NewsItem n = new NewsItem();
        n.setTitle(dto.getTitle());
        n.setSummary(dto.getSummary());
        n.setContent(dto.getContent());
        n.setPublished(dto.isPublished());
        if (n.isPublished()) n.setPublishedAt(Instant.now());
        NewsItem saved = newsRepository.save(n);
        if (saved.isPublished()) {
            eventPublisher.publishEvent(new NewsPublishedEvent(saved.getId(), saved.getTitle()));
        }
        return toNewsDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "publishedNews", allEntries = true)
    public NewsDto updateNews(Long id, NewsDto dto) {
        NewsItem n = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found: " + id));
        boolean wasPublished = n.isPublished();
        n.setTitle(dto.getTitle());
        n.setSummary(dto.getSummary());
        n.setContent(dto.getContent());
        n.setPublished(dto.isPublished());
        if (!wasPublished && dto.isPublished()) n.setPublishedAt(Instant.now());
        NewsItem saved = newsRepository.save(n);
        if (!wasPublished && saved.isPublished()) {
            eventPublisher.publishEvent(new NewsPublishedEvent(saved.getId(), saved.getTitle()));
        }
        return toNewsDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "publishedNews", allEntries = true)
    public void deleteNews(Long id) {
        NewsItem n = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found: " + id));
        newsRepository.delete(n);
    }

    private NewsDto toNewsDto(NewsItem n) {
        NewsDto d = new NewsDto();
        d.setId(n.getId());
        d.setTitle(n.getTitle());
        d.setSummary(n.getSummary());
        d.setContent(n.getContent());
        d.setPublished(n.isPublished());
        d.setPublishedAt(n.getPublishedAt());
        return d;
    }

    // Contact submissions
    @Override
    @Transactional
    public ContactSubmissionDto submitContact(ContactSubmissionRequest req, String ipAddress) {
        ContactSubmission s = new ContactSubmission();
        s.setName(req.getName());
        s.setEmail(req.getEmail());
        s.setPhone(req.getPhone());
        s.setMessage(req.getMessage());
        s.setIpAddress(ipAddress);
        ContactSubmission saved = contactRepository.save(s);
        return toContactDto(saved);
    }

    @Override
    public List<ContactSubmissionDto> listContactSubmissions() {
        return contactRepository.findAll()
                .stream()
                .map(this::toContactDto)
                .collect(Collectors.toList());
    }

    private ContactSubmissionDto toContactDto(ContactSubmission s) {
        ContactSubmissionDto d = new ContactSubmissionDto();
        d.setId(s.getId());
        d.setName(s.getName());
        d.setEmail(s.getEmail());
        d.setPhone(s.getPhone());
        d.setMessage(s.getMessage());
        d.setCreatedAt(s.getCreatedAt());
        return d;
    }
}
