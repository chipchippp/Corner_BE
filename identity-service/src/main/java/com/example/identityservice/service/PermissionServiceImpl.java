package com.example.identityservice.service;

import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.mapper.PermissionMapper;
import com.example.identityservice.model.Permission;
import com.example.identityservice.model.User;
import com.example.identityservice.repository.PermissionRepository;
import com.example.identityservice.service.impl.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public long save(PermissionRequest request) {
        log.info("Creating permission with name: {}", request.getName());
        Permission permission = permissionMapper.toPermission(request);
        // Check nếu name mới đã tồn tại và không phải là của name hiện tại
        if (!permission.getName().equals(request.getName()) &&
                permissionRepository.existsByName(request.getName())) {
            log.error("Permission with name {} already exists", request.getName());
            throw new AppException(ErrorCode.PERMISSION_NAME_ALREADY_EXISTS);
        }
        permissionRepository.save(permission);
        log.info("Permission with name {} created successfully", request.getName());
        return permission.getId();
    }

    @Override
    public PermissionResponse getPermissionById(Integer id) {
        log.info("Retrieving permission with id: {}", id);
        Permission permission = findPermissionById(id);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
        if (permissions.isEmpty()) {
            log.warn("No permissions found");
            return List.of();
        }
        log.info("Found {} permissions", permissions.size());
        return permissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    @Override
    public Permission updatePermission(Integer id, PermissionRequest request) {
        Permission permission = findPermissionById(id);

        // Kiểm tra nếu tên mới đã tồn tại và không phải của permission hiện tại
        if (!permission.getName().equals(request.getName()) &&
                permissionRepository.existsByName(request.getName())) {
            log.error("Permission with name {} already exists", request.getName());
            throw new AppException(ErrorCode.PERMISSION_NAME_ALREADY_EXISTS);
        }
        permissionMapper.updatePermission(request, permission);

        permissionRepository.save(permission);
        log.info("Permission with id: {} updated successfully", id);
        return permission;
    }

    @Override
    public void deletePermission(Integer id) {
        log.info("Deleting permission with id: {}", id);
        Permission permission = findPermissionById(id);
        permissionRepository.delete(permission);
        log.info("Permission with id: {} deleted successfully", id);
    }

    private Permission findPermissionById(int id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

}
