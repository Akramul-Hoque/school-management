package com.school.academic.mapper;

import com.school.academic.dto.SubjectDto;
import com.school.academic.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubjectMapper {
    SubjectDto toDto(Subject entity);
    Subject toEntity(SubjectDto dto);
}
