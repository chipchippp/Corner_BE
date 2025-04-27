package com.example.identityservice.dto.request;

import lombok.AccessLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    String username;
    String password;
}
