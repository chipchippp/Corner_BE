package com.example.identityservice.service;

import com.example.identityservice.dto.request.*;
import com.example.identityservice.dto.response.*;
import com.example.identityservice.exception.*;
import com.example.identityservice.mapper.RoleMapper;
import com.example.identityservice.model.Role;
import com.example.identityservice.model.User;
import com.example.identityservice.repository.*;
import com.example.identityservice.service.impl.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    @Override
    public long save(RoleRequest request) {
        log.info("Creating role with name: {}", request.getName());
        var role = roleMapper.toRole(request);

        // Check if the role name already exists
        if (roleRepository.existsByName(request.getName())) {
            log.error("Role with name {} already exists", request.getName());
            throw new AppException(ErrorCode.ROLE_NAME_ALREADY_EXISTS);
        }

        // Set permissions for the role
        var permissions = permissionRepository.findAllById(request.getPermissionId());
        role.setPermissions(new HashSet<>(permissions));

        // Save the role
        role = roleRepository.save(role);
        log.info("Role with id {} created successfully", role);
        return role.getId();
    }

    @Override
    public RoleResponse getRoleById(Integer id) {
        log.info("Retrieving role with id: {}", id);
        Role role = findRoleById(id);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRole() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public RoleResponse updateRole(Integer roleId, RoleRequest request) {
        Role role = findRoleById(roleId);

        // Check if the role name already exists and is not the current role
        if (!role.getName().equals(request.getName()) &&
                roleRepository.existsByName(request.getName())) {
            log.error("Role with name {} already exists", request.getName());
            throw new AppException(ErrorCode.ROLE_NAME_ALREADY_EXISTS);
        }

        // Update role fields
        roleMapper.updateRole(request, role);

        // Update permissions
        var permissions = permissionRepository.findAllById(request.getPermissionId());
        role.setPermissions(new HashSet<>(permissions));

        // Save the updated role
        role = roleRepository.save(role);
        log.info("Role with id {} updated successfully", roleId);

        // Return DTO instead of entity
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public void deleteRole(Integer roleId) {
        log.info("Deleting role with id: {}", roleId);
        Role role = findRoleById(roleId);

        if ("ADMIN".equalsIgnoreCase(role.getName())) {
            log.error("Cannot delete ADMIN role");
            throw new AppException(ErrorCode.CANNOT_DELETE_ADMIN_ROLE);
        }

        // Remove associations to avoid foreign key constraint issues
        role.setPermissions(null);
        roleRepository.delete(role);

        log.info("Role with id {} deleted successfully", roleId);
    }

    private Role findRoleById(int id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

}
