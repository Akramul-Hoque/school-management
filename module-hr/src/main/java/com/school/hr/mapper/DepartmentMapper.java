package com.school.hr.mapper;

import com.school.hr.dto.DepartmentDto;
import com.school.hr.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {
    DepartmentDto toDto(Department entity);
    Department toEntity(DepartmentDto dto);
    void updateEntity(DepartmentDto dto, @MappingTarget Department entity);
}
