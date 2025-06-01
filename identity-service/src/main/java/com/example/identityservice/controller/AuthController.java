package com.example.identityservice.controller;

import com.example.identityservice.dto.request.AuthRequest;
import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.dto.response.ApiResponse;
import com.example.identityservice.dto.response.AuthResponse;
import com.example.identityservice.dto.response.IntrospectResponse;
import com.example.identityservice.dto.response.ResponseError;
import com.example.identityservice.service.impl.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        try {
            log.info("Processing login request for user: {}", request.getUsername());
            var result = authService.authenticate(request);
            return new ApiResponse<>(HttpStatus.OK.value(), "Login successful", result);
        } catch (Exception e) {
            log.error("Login failed for user: {}. Error: {}", request.getUsername(), e.getMessage());
            return new ResponseError(HttpStatus.UNAUTHORIZED.value(), "Login failed");
        }
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        try {
            log.info("Processing introspection request for token: {}", request.getAccessToken());
            var result = authService.introspect(request);
            return new ApiResponse<>(HttpStatus.OK.value(), "Introspection successful", result);
        } catch (JOSEException | ParseException e) {
            log.error("Introspection failed for token: {}. Error: {}", request.getAccessToken(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Introspection failed");
        }
    }
}
