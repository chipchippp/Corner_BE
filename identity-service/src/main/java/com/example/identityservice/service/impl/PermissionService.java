package com.example.identityservice.service.impl;

import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.model.Permission;

import java.util.List;

public interface PermissionService {
    long save(PermissionRequest request);
    PermissionResponse getPermissionById(Integer id);
    List<PermissionResponse> getAllPermissions();
    Permission updatePermission(Integer id, PermissionRequest request);
    void deletePermission(Integer id);
}
