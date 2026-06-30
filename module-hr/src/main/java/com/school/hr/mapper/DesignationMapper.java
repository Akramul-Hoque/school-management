package com.school.hr.mapper;

import com.school.hr.dto.DesignationDto;
import com.school.hr.entity.Designation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DesignationMapper {
    DesignationDto toDto(Designation entity);
    Designation toEntity(DesignationDto dto);
    void updateEntity(DesignationDto dto, @MappingTarget Designation entity);
}
