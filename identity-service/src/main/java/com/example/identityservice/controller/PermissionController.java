package com.example.identityservice.controller;

import com.example.identityservice.configuration.Translator;
import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.response.ApiResponse;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.dto.response.ResponseError;
import com.example.identityservice.service.impl.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    public ApiResponse<Long> createPermission(@RequestBody @Valid PermissionRequest request) {
        try {
            log.info("Creating permission with name: {}", request.getName());
            long id = permissionService.save(request);
            return new ApiResponse<>(HttpStatus.CREATED.value(), "Permission created successfully", id);
        } catch (Exception e) {
            log.error("Error creating permission: {}", e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Failed to create permission: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Permissions retrieved successfully",
                permissionService.getAllPermissions()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<PermissionResponse> getPermissionById(@PathVariable Integer id) {
        try {
            log.info("Retrieving permission with id: {}", id);
            PermissionResponse permission = permissionService.getPermissionById(id);
            return new ApiResponse<>(HttpStatus.OK.value(), "Permission retrieved successfully", permission);
        } catch (Exception e) {
            log.error("Error retrieving permission: {}", e.getMessage());
            return new ResponseError(HttpStatus.NOT_FOUND.value(), "Permission not found: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updatePermission(@PathVariable Integer id, @RequestBody @Valid PermissionRequest request) {
        try {
            log.info("Updating permission with id: {}", id);
            permissionService.updatePermission(id, request);
            return new ApiResponse<>(HttpStatus.ACCEPTED.value(), "Permission updated successfully");
        } catch (Exception e) {
            log.error("Error updating permission: {}", e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Failed to update permission: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deletePermission(@PathVariable Integer id) {
        try {
            log.info("Deleting permission with id: {}", id);
            permissionService.deletePermission(id);
            return new ApiResponse<>(HttpStatus.OK.value(), "Permission deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting permission: {}", e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Failed to delete permission: " + e.getMessage());
        }
    }

}
