package com.example.identityservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    Integer id;
    String name;
    String description;
    Set<PermissionResponse> permissions;
}
