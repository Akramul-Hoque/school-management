package com.school.student.mapper;

import com.school.student.dto.GuardianDto;
import com.school.student.entity.Guardian;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GuardianMapper {
    GuardianDto toDto(Guardian entity);
    Guardian toEntity(GuardianDto dto);
}
