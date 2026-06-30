package com.school.student.service.impl;

import com.school.academic.service.api.AcademicService;
import com.school.common.exception.BusinessRuleException;
import com.school.common.exception.ResourceNotFoundException;
import com.school.student.dto.StudentDto;
import com.school.student.dto.StudentSearchFilter;
import com.school.student.entity.Guardian;
import com.school.student.entity.Student;
import com.school.student.event.StudentCreatedEvent;
import com.school.student.event.StudentPromotedEvent;
import com.school.student.mapper.StudentMapper;
import com.school.student.repository.GuardianRepository;
import com.school.student.repository.StudentRepository;
import com.school.student.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final StudentMapper studentMapper;
    
    private final AcademicService academicService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public StudentDto createStudentFromApplication(StudentDto studentDto) {
        if (!academicService.isValidClassSection(studentDto.getCurrentClassId(), studentDto.getCurrentSectionId())) {
            throw new BusinessRuleException("Invalid class/section assignment");
        }
        
        Guardian guardian = studentMapper.toEntity(studentDto).getGuardian();
        if (guardian != null) {
            guardian = guardianRepository.save(guardian);
        }

        Student student = studentMapper.toEntity(studentDto);
        student.setGuardian(guardian);
        student = studentRepository.save(student);

        StudentCreatedEvent event = new StudentCreatedEvent(student.getId(), student.getStudentCode(), student.getEmail());
        rabbitTemplate.convertAndSend("student.exchange", "student.created", event);

        return studentMapper.toDto(student);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return studentMapper.toDto(student);
    }

    @Override
    public Page<StudentDto> search(StudentSearchFilter filter, Pageable pageable) {
        // Simplified search for brevity
        if (filter.getClassId() != null && filter.getSectionId() != null) {
            return studentRepository.findByCurrentClassIdAndCurrentSectionId(filter.getClassId(), filter.getSectionId(), pageable)
                    .map(studentMapper::toDto);
        }
        return studentRepository.findAll(pageable).map(studentMapper::toDto);
    }

    @Override
    @Transactional
    public void promoteStudents(Long fromSessionId, Long toSessionId) {
        // Mock promotion logic
        StudentPromotedEvent event = new StudentPromotedEvent(fromSessionId, toSessionId, null);
        rabbitTemplate.convertAndSend("student.exchange", "student.promoted", event);
    }

    @Override
    public byte[] generateTransferCertificate(Long studentId) {
        // Mock PDF generation
        return "Transfer Certificate PDF content".getBytes();
    }
}
