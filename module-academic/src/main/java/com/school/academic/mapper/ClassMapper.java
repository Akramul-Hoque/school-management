package com.school.academic.mapper;

import com.school.academic.dto.ClassDto;
import com.school.academic.entity.ClassEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClassMapper {
    ClassDto toDto(ClassEntity entity);
    ClassEntity toEntity(ClassDto dto);
}
