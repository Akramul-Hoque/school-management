package com.school.hr.mapper;

import com.school.hr.dto.LeaveBalanceDto;
import com.school.hr.entity.LeaveBalance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaveBalanceMapper {
    @Mapping(source = "staff.id", target = "staffId")
    LeaveBalanceDto toDto(LeaveBalance entity);
}
