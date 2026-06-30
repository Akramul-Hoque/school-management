package com.school.hr.mapper;

import com.school.hr.dto.LeaveRequestDto;
import com.school.hr.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaveRequestMapper {
    @Mapping(source = "staff.id", target = "staffId")
    @Mapping(target = "staffName", expression = "java(entity.getStaff().getFirstName() + \" \" + entity.getStaff().getLastName())")
    LeaveRequestDto toDto(LeaveRequest entity);
}
