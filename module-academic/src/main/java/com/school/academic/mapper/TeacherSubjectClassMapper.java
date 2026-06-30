package com.school.academic.mapper;

import com.school.academic.dto.TeacherAssignmentDto;
import com.school.academic.entity.TeacherSubjectClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeacherSubjectClassMapper {
    @Mapping(source = "classEntity.id", target = "classId")
    @Mapping(source = "classEntity.name", target = "className")
    @Mapping(source = "section.id", target = "sectionId")
    @Mapping(source = "section.name", target = "sectionName")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.name", target = "subjectName")
    @Mapping(source = "session.id", target = "sessionId")
    TeacherAssignmentDto toDto(TeacherSubjectClass entity);
}
