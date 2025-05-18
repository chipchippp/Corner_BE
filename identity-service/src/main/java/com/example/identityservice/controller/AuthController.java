package com.example.identityservice.controller;

import com.example.identityservice.dto.request.AuthRequest;
import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.dto.response.ApiResponse;
import com.example.identityservice.dto.response.AuthResponse;
import com.example.identityservice.dto.response.IntrospectResponse;
import com.example.identityservice.service.impl.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Slf4j
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    AuthService authService;

    @PostMapping("/token")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        var result = authService.authenticate(request);
        log.info("User {} authenticated: {}", request.getUsername(), result);
        return ApiResponse.<AuthResponse>builder()
                .data(result)
                .message("User authenticated successfully")
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> in(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .data(result)
                .message("Token introspected successfully")
                .build();
    }
}
