package com.school.auth.mapper;

import com.school.auth.dto.PermissionDto;
import com.school.auth.dto.RoleDto;
import com.school.auth.entity.Permission;
import com.school.auth.entity.Role;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-23T17:01:22+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public RoleDto toDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setId( role.getId() );
        roleDto.setName( role.getName() );
        roleDto.setDescription( role.getDescription() );
        roleDto.setPermissions( permissionSetToPermissionDtoSet( role.getPermissions() ) );

        return roleDto;
    }

    @Override
    public Role toEntity(RoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( dto.getId() );
        role.setName( dto.getName() );
        role.setDescription( dto.getDescription() );
        role.setPermissions( permissionDtoSetToPermissionSet( dto.getPermissions() ) );

        return role;
    }

    protected Set<PermissionDto> permissionSetToPermissionDtoSet(Set<Permission> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionDto> set1 = new LinkedHashSet<PermissionDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permission permission : set ) {
            set1.add( permissionMapper.toDto( permission ) );
        }

        return set1;
    }

    protected Set<Permission> permissionDtoSetToPermissionSet(Set<PermissionDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Permission> set1 = new LinkedHashSet<Permission>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PermissionDto permissionDto : set ) {
            set1.add( permissionMapper.toEntity( permissionDto ) );
        }

        return set1;
    }
}
