package com.school.auth.controller;

import com.school.auth.dto.*;
import com.school.auth.service.impl.AuthService;
import com.school.common.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ApiResponse.success("Login successful", response);
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenRefreshResponse> refresh(@Valid @RequestBody TokenRefreshRequest request) {
        TokenRefreshResponse response = authService.refresh(request);
        return ApiResponse.success("Token refreshed successfully", response);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(Principal principal) {
        if (principal != null) {
            authService.logout(principal.getName());
        }
        return ApiResponse.success("Logout successful", null);
    }

    @PostMapping("/change-password")
    public ApiResponse<Void> changePassword(Principal principal, @Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(principal.getName(), request);
        return ApiResponse.success("Password changed successfully", null);
    }
}
