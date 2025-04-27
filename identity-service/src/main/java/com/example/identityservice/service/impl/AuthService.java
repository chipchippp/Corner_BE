package com.example.identityservice.service.impl;

import com.example.identityservice.dto.request.AuthRequest;

public interface AuthService {
    boolean authenticate(AuthRequest request);
}
