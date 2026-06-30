package com.school.admission.service.impl;

import com.school.academic.service.api.AcademicService;
import com.school.admission.dto.AdmissionApplicationDto;
import com.school.admission.entity.AdmissionApplication;
import com.school.admission.mapper.AdmissionMapper;
import com.school.admission.repository.AdmissionApplicationRepository;
import com.school.admission.service.api.AdmissionService;
import com.school.common.exception.BusinessRuleException;
import com.school.common.exception.ResourceNotFoundException;
import com.school.student.dto.GuardianDto;
import com.school.student.dto.StudentDto;
import com.school.student.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionApplicationRepository applicationRepository;
    private final AdmissionMapper admissionMapper;
    
    private final AcademicService academicService;
    private final StudentService studentService;

    @Override
    @Transactional
    public AdmissionApplicationDto submitApplication(AdmissionApplicationDto dto) {
        AdmissionApplication app = admissionMapper.toEntity(dto);
        app.setApplicationNumber("APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        app.setStatus("PENDING");
        return admissionMapper.toDto(applicationRepository.save(app));
    }

    @Override
    public AdmissionApplicationDto getApplicationById(Long id) {
        return admissionMapper.toDto(applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found")));
    }

    @Override
    public Page<AdmissionApplicationDto> getApplicationsByStatus(String status, Pageable pageable) {
        return applicationRepository.findByStatus(status, pageable).map(admissionMapper::toDto);
    }

    @Override
    @Transactional
    public void approveApplication(Long applicationId, Long sectionId) {
        AdmissionApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (!"PENDING".equals(app.getStatus())) {
            throw new BusinessRuleException("Application is already processed");
        }

        if (!academicService.isValidClassSection(app.getAppliedClassId(), sectionId)) {
            throw new BusinessRuleException("Invalid section for the applied class");
        }

        app.setStatus("APPROVED");
        applicationRepository.save(app);

        // Map to StudentDto and call Student module synchronously
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName(app.getFirstName());
        studentDto.setLastName(app.getLastName());
        studentDto.setEmail(app.getEmail());
        studentDto.setDateOfBirth(app.getDateOfBirth());
        studentDto.setGender(app.getGender());
        studentDto.setAdmissionDate(LocalDate.now());
        studentDto.setActive(true);
        studentDto.setStudentCode("STU-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        studentDto.setCurrentClassId(app.getAppliedClassId());
        studentDto.setCurrentSectionId(sectionId);
        studentDto.setCurrentSessionId(app.getAppliedSessionId());
        
        GuardianDto guardianDto = new GuardianDto();
        guardianDto.setFirstName(app.getGuardianFirstName());
        guardianDto.setLastName(app.getGuardianLastName());
        guardianDto.setEmail(app.getGuardianEmail());
        guardianDto.setPhoneNumber(app.getGuardianPhone());
        guardianDto.setRelationToStudent(app.getGuardianRelation());
        studentDto.setGuardian(guardianDto);

        // Direct call across modules in same transaction
        studentService.createStudentFromApplication(studentDto);
    }

    @Override
    @Transactional
    public void rejectApplication(Long applicationId) {
        AdmissionApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (!"PENDING".equals(app.getStatus())) {
            throw new BusinessRuleException("Application is already processed");
        }

        app.setStatus("REJECTED");
        applicationRepository.save(app);
    }
}
