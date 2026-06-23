package com.school.auth.mapper;

import com.school.auth.dto.UserDto;
import com.school.auth.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
