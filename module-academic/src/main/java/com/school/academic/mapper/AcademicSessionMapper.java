package com.school.academic.mapper;

import com.school.academic.dto.AcademicSessionDto;
import com.school.academic.entity.AcademicSession;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcademicSessionMapper {
    AcademicSessionDto toDto(AcademicSession entity);
    AcademicSession toEntity(AcademicSessionDto dto);
}
