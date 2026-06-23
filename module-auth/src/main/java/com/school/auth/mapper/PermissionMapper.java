package com.school.auth.mapper;

import com.school.auth.dto.PermissionDto;
import com.school.auth.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDto toDto(Permission permission);
    Permission toEntity(PermissionDto dto);
}
