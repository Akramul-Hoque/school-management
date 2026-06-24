package com.school.admission.service.impl;

import com.school.admission.dto.AdmissionApplicationDto;
import com.school.admission.dto.AdmissionApplicationRequest;
import com.school.admission.entity.AdmissionApplication;
import com.school.admission.event.AdmissionApprovedEvent;
import com.school.admission.repository.AdmissionApplicationRepository;
import com.school.admission.service.api.AdmissionService;
import com.school.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionApplicationRepository repository;
    private final AmqpTemplate amqpTemplate;
    // StudentService is an external module dependency; call directly per architectural rule
    private final com.school.student.service.api.StudentService studentService;

    @Override
    @Transactional
    public AdmissionApplicationDto submit(AdmissionApplicationRequest req) {
        AdmissionApplication a = new AdmissionApplication();
        a.setApplicantName(req.getApplicantName());
        a.setEmail(req.getEmail());
        a.setPhone(req.getPhone());
        a.setClassAppliedId(req.getClassAppliedId());
        AdmissionApplication saved = repository.save(a);
        return toDto(saved);
    }

    @Override
    @Transactional
    public Long approveAndConvert(Long applicationId) {
        AdmissionApplication a = repository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found: " + applicationId));

        if (a.getStatus() != AdmissionApplication.Status.PENDING) {
            throw new IllegalStateException("Only PENDING applications can be approved");
        }

        // Call StudentService to create student in the same transaction
        com.school.student.dto.StudentDto studentDto = studentService.createStudentFromApplication(toDto(a));

        a.setStatus(AdmissionApplication.Status.APPROVED);
        repository.save(a);

        // Publish event to RabbitMQ so other modules (fee, notification) can consume
        AdmissionApprovedEvent event = new AdmissionApprovedEvent(a.getId(), studentDto.getId(), a.getEmail());
        amqpTemplate.convertAndSend("admission.exchange", "admission.approved", event);

        return studentDto.getId();
    }

    @Override
    public AdmissionApplicationDto getById(Long id) {
        AdmissionApplication a = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found: " + id));
        return toDto(a);
    }

    @Override
    @Transactional
    public void reject(Long id) {
        AdmissionApplication a = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found: " + id));
        a.setStatus(AdmissionApplication.Status.REJECTED);
        repository.save(a);
    }

    private AdmissionApplicationDto toDto(AdmissionApplication a) {
        AdmissionApplicationDto d = new AdmissionApplicationDto();
        d.setId(a.getId());
        d.setApplicantName(a.getApplicantName());
        d.setEmail(a.getEmail());
        d.setPhone(a.getPhone());
        d.setClassAppliedId(a.getClassAppliedId());
        d.setStatus(a.getStatus().name());
        d.setCreatedAt(a.getCreatedAt());
        return d;
    }
}
