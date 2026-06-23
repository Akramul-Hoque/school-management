package com.school.auth.mapper;

import com.school.auth.dto.RoleDto;
import com.school.auth.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto dto);
}
