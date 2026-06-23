package com.school.auth.controller;

import com.school.auth.dto.UserDto;
import com.school.auth.service.api.AuthUserService;
import com.school.common.model.ApiResponse;
import com.school.common.model.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AuthUserService authUserService;

    @GetMapping
    public ApiResponse<PageResponse<UserDto>> getAllUsers(Pageable pageable) {
        Page<UserDto> users = authUserService.getAllUsers(pageable);
        return ApiResponse.success("Fetched all users", PageResponse.from(users));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = authUserService.getUserById(id);
        return ApiResponse.success("Fetched user", user);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        UserDto updated = authUserService.updateUser(id, userDto);
        return ApiResponse.success("Updated user successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        authUserService.deleteUser(id);
        return ApiResponse.success("Deleted user successfully", null);
    }
}
