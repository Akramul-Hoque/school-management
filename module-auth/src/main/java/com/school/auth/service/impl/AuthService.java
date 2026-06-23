package com.school.auth.service.impl;

import com.school.auth.dto.*;
import com.school.auth.entity.RefreshToken;
import com.school.auth.entity.Role;
import com.school.auth.entity.User;
import com.school.auth.repository.RefreshTokenRepository;
import com.school.auth.repository.UserRepository;
import com.school.auth.security.JwtTokenProvider;
import com.school.common.exception.BusinessRuleException;
import com.school.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;

    private static final long REFRESH_TOKEN_EXPIRATION_MS = 604800000; // 7 days

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessRuleException("Invalid username or password"));

        if (!user.isEnabled()) {
            throw new BusinessRuleException("User account is disabled");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessRuleException("Invalid username or password");
        }

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String accessToken = jwtTokenProvider.generateToken(user.getUsername(), roles);
        RefreshToken refreshToken = createRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken.getToken(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }

    public TokenRefreshResponse refresh(TokenRefreshRequest request) {
        String tokenStr = request.getRefreshToken();
        RefreshToken refreshToken = refreshTokenRepository.findByToken(tokenStr)
                .orElseThrow(() -> new BusinessRuleException("Refresh token not found"));

        if (refreshToken.isRevoked()) {
            throw new BusinessRuleException("Refresh token has been revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new BusinessRuleException("Refresh token has expired");
        }

        User user = refreshToken.getUser();
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String accessToken = jwtTokenProvider.generateToken(user.getUsername(), roles);
        
        // Rotate refresh token
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        RefreshToken newRefreshToken = createRefreshToken(user);

        return new TokenRefreshResponse(accessToken, newRefreshToken.getToken());
    }

    public void logout(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        
        // Evict cached roles directly via CacheManager
        Cache cache = cacheManager.getCache("userRoles");
        if (cache != null) {
            cache.evict(user.getId());
        }
        
        refreshTokenRepository.deleteByUser(user);
    }

    public void changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BusinessRuleException("Old password does not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION_MS));
        refreshToken.setRevoked(false);
        return refreshTokenRepository.save(refreshToken);
    }
}
