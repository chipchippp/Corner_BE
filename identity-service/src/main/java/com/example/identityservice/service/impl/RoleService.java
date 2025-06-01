package com.example.identityservice.service.impl;

import com.example.identityservice.dto.request.RoleRequest;
import com.example.identityservice.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    long save(RoleRequest request);
    RoleResponse getRoleById(Integer id);
    List<RoleResponse> getAllRole();
    RoleResponse updateRole(Integer roleId, RoleRequest request);
    void deleteRole(Integer roleId);
}
