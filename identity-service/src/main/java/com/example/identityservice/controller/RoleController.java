package com.example.identityservice.controller;

import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.request.RoleRequest;
import com.example.identityservice.dto.response.ApiResponse;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.dto.response.ResponseError;
import com.example.identityservice.dto.response.RoleResponse;
import com.example.identityservice.service.impl.PermissionService;
import com.example.identityservice.service.impl.RoleService;
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
@RequestMapping("/api/v1/role")
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ApiResponse<Long> create(@RequestBody @Valid RoleRequest request) {
        try {
            log.info("Creating role with name: {}", request.getName());
            long id = roleService.save(request);
            return new ApiResponse<>(HttpStatus.CREATED.value(), "Role created successfully", id);
        } catch (Exception e) {
            log.error("Error creating role: {}", e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Failed to create role: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<List<RoleResponse>> getAll() {
        try {
            return new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Role retrieved successfully",
                    roleService.getAllRole()
            );
        } catch (Exception e) {
            e.printStackTrace(); // log rõ lỗi
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve roles: " + e.getMessage(),
                    null
            );
        }
    }


    @GetMapping("/{id}")
    public ApiResponse<RoleResponse> getRoleById(@PathVariable Integer id) {
        try {
            log.info("Retrieving role with id: {}", id);
            RoleResponse role = roleService.getRoleById(id);
            return new ApiResponse<>(HttpStatus.OK.value(), "Role retrieved successfully", role);
        } catch (Exception e) {
            log.error("Error retrieving role: {}", e.getMessage());
            return new ResponseError(HttpStatus.NOT_FOUND.value(), "Role not found: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Integer id, @RequestBody @Valid RoleRequest request) {
        try {
            log.info("Updating role with id: {}", id);
            RoleResponse updatedRole = roleService.updateRole(id, request);
            return new ApiResponse<>(HttpStatus.ACCEPTED.value(), "Role updated successfully", updatedRole);
        } catch (Exception e) {
            log.error("Error updating role: {}", e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Failed to update role: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Integer id) {
        try {
            log.info("Deleting role with id: {}", id);
            roleService.deleteRole(id);
            return new ApiResponse<>(HttpStatus.OK.value(), "Role deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting role: {}", e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Failed to delete role: " + e.getMessage());
        }
    }

}
