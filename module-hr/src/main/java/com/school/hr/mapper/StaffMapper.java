package com.school.hr.mapper;

import com.school.hr.dto.StaffDto;
import com.school.hr.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StaffMapper {
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "designation.id", target = "designationId")
    @Mapping(source = "designation.name", target = "designationName")
    StaffDto toDto(Staff entity);

    @Mapping(target = "department", ignore = true)
    @Mapping(target = "designation", ignore = true)
    Staff toEntity(StaffDto dto);

    @Mapping(target = "department", ignore = true)
    @Mapping(target = "designation", ignore = true)
    void updateEntity(StaffDto dto, @MappingTarget Staff entity);
}
