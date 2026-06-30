package com.school.academic.service.impl;

import com.school.academic.dto.*;
import com.school.academic.entity.*;
import com.school.academic.mapper.*;
import com.school.academic.repository.*;
import com.school.academic.service.api.AcademicService;
import com.school.common.exception.BusinessRuleException;
import com.school.common.exception.ResourceNotFoundException;
import com.school.hr.service.api.HrService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AcademicServiceImpl implements AcademicService {

    private final AcademicSessionRepository sessionRepository;
    private final ClassEntityRepository classRepository;
    private final SectionRepository sectionRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherSubjectClassRepository teacherSubjectClassRepository;
    private final RoutineEntryRepository routineEntryRepository;

    private final AcademicSessionMapper sessionMapper;
    private final ClassMapper classMapper;
    private final SectionMapper sectionMapper;
    private final SubjectMapper subjectMapper;
    private final TeacherSubjectClassMapper teacherSubjectClassMapper;
    private final RoutineEntryMapper routineEntryMapper;

    // Direct interface call to HR module
    private final HrService hrService;

    @Override
    @Cacheable(value = "currentSession")
    public AcademicSessionDto getCurrentSession() {
        AcademicSession session = sessionRepository.findByCurrentTrue()
                .orElseThrow(() -> new ResourceNotFoundException("No active academic session found"));
        return sessionMapper.toDto(session);
    }

    @Override
    @Cacheable(value = "sessionClasses", key = "#sessionId")
    public List<ClassDto> getClassesForSession(Long sessionId) {
        // Typically all classes might apply to a session, or it could be mapped.
        return classRepository.findAll().stream().map(classMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "classSubjects", key = "#classId")
    public List<SubjectDto> getSubjectsForClass(Long classId) {
        return subjectRepository.findAll().stream().map(subjectMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public boolean isValidClassSection(Long classId, Long sectionId) {
        // In a real system, there might be a ClassSection mapping table.
        // Assuming true if both exist for simplicity, or check TeacherSubjectClass.
        return classRepository.existsById(classId) && sectionRepository.existsById(sectionId);
    }

    @Override
    public List<TeacherAssignmentDto> getAssignmentsForTeacher(Long staffId) {
        AcademicSession current = sessionRepository.findByCurrentTrue().orElseThrow(() -> new BusinessRuleException("No current session"));
        return teacherSubjectClassRepository.findByStaffIdAndSessionId(staffId, current.getId())
                .stream().map(teacherSubjectClassMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AcademicSessionDto> getAllSessions() {
        return sessionRepository.findAll().stream().map(sessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "currentSession", allEntries = true)
    public AcademicSessionDto createSession(AcademicSessionDto dto) {
        AcademicSession entity = sessionMapper.toEntity(dto);
        return sessionMapper.toDto(sessionRepository.save(entity));
    }

    @Override
    public List<ClassDto> getAllClasses() {
        return classRepository.findAll().stream().map(classMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "sessionClasses", allEntries = true)
    public ClassDto createClass(ClassDto dto) {
        ClassEntity entity = classMapper.toEntity(dto);
        return classMapper.toDto(classRepository.save(entity));
    }

    @Override
    public List<SectionDto> getAllSections() {
        return sectionRepository.findAll().stream().map(sectionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SectionDto createSection(SectionDto dto) {
        Section entity = sectionMapper.toEntity(dto);
        return sectionMapper.toDto(sectionRepository.save(entity));
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream().map(subjectMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "classSubjects", allEntries = true)
    public SubjectDto createSubject(SubjectDto dto) {
        Subject entity = subjectMapper.toEntity(dto);
        return subjectMapper.toDto(subjectRepository.save(entity));
    }

    @Override
    @Transactional
    public TeacherAssignmentDto mapTeacherToClass(TeacherAssignmentDto dto) {
        hrService.getStaffById(dto.getStaffId()); // Validate staff exists via HR module
        
        ClassEntity cls = classRepository.findById(dto.getClassId()).orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        Section sec = sectionRepository.findById(dto.getSectionId()).orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        Subject sub = subjectRepository.findById(dto.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        AcademicSession session = sessionRepository.findById(dto.getSessionId()).orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        TeacherSubjectClass assignment = new TeacherSubjectClass();
        assignment.setStaffId(dto.getStaffId());
        assignment.setClassEntity(cls);
        assignment.setSection(sec);
        assignment.setSubject(sub);
        assignment.setSession(session);
        
        return teacherSubjectClassMapper.toDto(teacherSubjectClassRepository.save(assignment));
    }

    @Override
    @Transactional
    public RoutineEntryDto createRoutineEntry(RoutineEntryDto dto) {
        ClassEntity cls = classRepository.findById(dto.getClassId()).orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        Section sec = sectionRepository.findById(dto.getSectionId()).orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        Subject sub = subjectRepository.findById(dto.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        AcademicSession session = sessionRepository.findById(dto.getSessionId()).orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        RoutineEntry entry = new RoutineEntry();
        entry.setClassEntity(cls);
        entry.setSection(sec);
        entry.setSubject(sub);
        entry.setSession(session);
        entry.setStaffId(dto.getStaffId());
        entry.setDayOfWeek(dto.getDayOfWeek());
        entry.setStartTime(dto.getStartTime());
        entry.setEndTime(dto.getEndTime());
        
        return routineEntryMapper.toDto(routineEntryRepository.save(entry));
    }

    @Override
    public List<RoutineEntryDto> getRoutineForClass(Long classId, Long sectionId, Long sessionId) {
        return routineEntryRepository.findByClassEntityIdAndSectionIdAndSessionId(classId, sectionId, sessionId)
                .stream().map(routineEntryMapper::toDto).collect(Collectors.toList());
    }
}
