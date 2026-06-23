package com.school.auth.mapper;

import com.school.auth.dto.PermissionDto;
import com.school.auth.entity.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-23T17:01:22+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public PermissionDto toDto(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionDto permissionDto = new PermissionDto();

        permissionDto.setId( permission.getId() );
        permissionDto.setName( permission.getName() );
        permissionDto.setDescription( permission.getDescription() );

        return permissionDto;
    }

    @Override
    public Permission toEntity(PermissionDto dto) {
        if ( dto == null ) {
            return null;
        }

        Permission permission = new Permission();

        permission.setId( dto.getId() );
        permission.setName( dto.getName() );
        permission.setDescription( dto.getDescription() );

        return permission;
    }
}
