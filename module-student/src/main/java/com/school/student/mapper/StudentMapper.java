package com.school.student.mapper;

import com.school.student.dto.StudentDto;
import com.school.student.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {GuardianMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    StudentDto toDto(Student entity);
    Student toEntity(StudentDto dto);
}
