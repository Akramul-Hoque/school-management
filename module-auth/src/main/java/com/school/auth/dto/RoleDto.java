package com.school.auth.dto;

import lombok.Data;
import java.util.Set;

@Data
public class RoleDto {
    private Long id;
    private String name;
    private String description;
    private Set<PermissionDto> permissions;
}
