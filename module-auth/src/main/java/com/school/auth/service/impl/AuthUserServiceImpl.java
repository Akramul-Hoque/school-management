package com.school.auth.service.impl;

import com.school.auth.dto.RoleDto;
import com.school.auth.dto.UserDto;
import com.school.auth.entity.Role;
import com.school.auth.entity.User;
import com.school.auth.mapper.RoleMapper;
import com.school.auth.mapper.UserMapper;
import com.school.auth.repository.RoleRepository;
import com.school.auth.repository.UserRepository;
import com.school.auth.service.api.AuthUserService;
import com.school.common.exception.BusinessRuleException;
import com.school.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthUserServiceImpl implements AuthUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userMapper.toDto(user);
    }

    @Override
    @Cacheable(value = "userRoles", key = "#userId")
    public Set<String> getRolesForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public UserDto createPortalLogin(Long linkedStaffOrStudentId, String username, String email, String roleName) {
        if (userRepository.existsByUsername(username)) {
            throw new BusinessRuleException("Username '" + username + "' is already taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new BusinessRuleException("Email '" + email + "' is already registered");
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName));

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setLinkedId(linkedStaffOrStudentId);
        user.setEnabled(true);
        
        // Default password = username + "@123"
        String defaultPassword = username + "@123";
        user.setPassword(passwordEncoder.encode(defaultPassword));
        
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setEmail(userDto.getEmail());
        user.setEnabled(userDto.isEnabled());
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "rolePermissions", key = "#id")
    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return roleMapper.toDto(role);
    }

    @Override
    @Transactional
    public RoleDto createRole(RoleDto roleDto) {
        if (roleRepository.findByName(roleDto.getName()).isPresent()) {
            throw new BusinessRuleException("Role with name '" + roleDto.getName() + "' already exists");
        }
        Role role = roleMapper.toEntity(roleDto);
        Role saved = roleRepository.save(role);
        return roleMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CachePut(value = "rolePermissions", key = "#id")
    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        
        Role updatedEntity = roleMapper.toEntity(roleDto);
        role.setPermissions(updatedEntity.getPermissions());
        
        Role saved = roleRepository.save(role);
        return roleMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "rolePermissions", key = "#id")
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        roleRepository.delete(role);
    }
}
