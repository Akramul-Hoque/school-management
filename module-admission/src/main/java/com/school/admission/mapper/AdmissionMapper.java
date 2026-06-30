package com.school.admission.mapper;

import com.school.admission.dto.AdmissionApplicationDto;
import com.school.admission.entity.AdmissionApplication;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdmissionMapper {
    AdmissionApplicationDto toDto(AdmissionApplication entity);
    AdmissionApplication toEntity(AdmissionApplicationDto dto);
}
