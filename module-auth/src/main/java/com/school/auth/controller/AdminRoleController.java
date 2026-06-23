package com.school.auth.controller;

import com.school.auth.dto.RoleDto;
import com.school.auth.service.api.AuthUserService;
import com.school.common.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
public class AdminRoleController {

    private final AuthUserService authUserService;

    @GetMapping
    public ApiResponse<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = authUserService.getAllRoles();
        return ApiResponse.success("Fetched all roles", roles);
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleDto> getRoleById(@PathVariable Long id) {
        RoleDto role = authUserService.getRoleById(id);
        return ApiResponse.success("Fetched role", role);
    }

    @PostMapping
    public ApiResponse<RoleDto> createRole(@Valid @RequestBody RoleDto roleDto) {
        RoleDto created = authUserService.createRole(roleDto);
        return ApiResponse.success("Created role successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<RoleDto> updateRole(@PathVariable Long id, @Valid @RequestBody RoleDto roleDto) {
        RoleDto updated = authUserService.updateRole(id, roleDto);
        return ApiResponse.success("Updated role successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRole(@PathVariable Long id) {
        authUserService.deleteRole(id);
        return ApiResponse.success("Deleted role successfully", null);
    }
}
