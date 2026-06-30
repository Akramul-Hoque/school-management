package com.school.academic.service.api;

import com.school.academic.dto.*;
import java.util.List;

public interface AcademicService {
    AcademicSessionDto getCurrentSession();
    List<ClassDto> getClassesForSession(Long sessionId);
    List<SubjectDto> getSubjectsForClass(Long classId);
    boolean isValidClassSection(Long classId, Long sectionId);
    List<TeacherAssignmentDto> getAssignmentsForTeacher(Long staffId);
    
    // CRUD ops usually needed:
    List<AcademicSessionDto> getAllSessions();
    AcademicSessionDto createSession(AcademicSessionDto dto);
    
    List<ClassDto> getAllClasses();
    ClassDto createClass(ClassDto dto);
    
    List<SectionDto> getAllSections();
    SectionDto createSection(SectionDto dto);
    
    List<SubjectDto> getAllSubjects();
    SubjectDto createSubject(SubjectDto dto);
    
    TeacherAssignmentDto mapTeacherToClass(TeacherAssignmentDto dto);
    RoutineEntryDto createRoutineEntry(RoutineEntryDto dto);
    List<RoutineEntryDto> getRoutineForClass(Long classId, Long sectionId, Long sessionId);
}
