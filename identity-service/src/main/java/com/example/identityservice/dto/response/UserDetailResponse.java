package com.example.identityservice.dto.response;

import com.example.identityservice.dto.validator.Gender;
import com.example.identityservice.util.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailResponse implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String username;
    String email;
    String phone;
    Gender gender;
    UserStatus status;
    Set<RoleResponse> roles;
}
