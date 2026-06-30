package com.school.academic.mapper;

import com.school.academic.dto.SectionDto;
import com.school.academic.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SectionMapper {
    SectionDto toDto(Section entity);
    Section toEntity(SectionDto dto);
}
