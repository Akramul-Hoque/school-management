package com.school.auth.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private Long linkedId;
    private Set<RoleDto> roles;
}
