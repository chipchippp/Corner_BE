package com.example.identityservice.mapper;

import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.model.Permission;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
    void updatePermission(PermissionRequest request, @MappingTarget Permission permission);
}
