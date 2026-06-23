package com.school.auth.service.api;

import com.school.auth.dto.RoleDto;
import com.school.auth.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

public interface AuthUserService {
    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);
    Set<String> getRolesForUser(Long userId);
    UserDto createPortalLogin(Long linkedStaffOrStudentId, String username, String email, String roleName);
    Page<UserDto> getAllUsers(Pageable pageable);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(Long id);
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(Long id, RoleDto roleDto);
    void deleteRole(Long id);
}
