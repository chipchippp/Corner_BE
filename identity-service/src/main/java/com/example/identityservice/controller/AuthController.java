package com.example.identityservice.controller;

import com.example.identityservice.dto.request.AuthRequest;
import com.example.identityservice.dto.response.ApiResponse;
import com.example.identityservice.dto.response.AuthResponse;
import com.example.identityservice.service.impl.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        boolean result = authService.authenticate(request);
        log.info("User {} authenticated: {}", request.getUsername(), result);
        return ApiResponse.<AuthResponse>builder()
                .data(AuthResponse.builder()
                        .authenticated(result)
                        .build())
                .build();
    }
}
