package com.example.identityservice.service.impl;

import com.example.identityservice.dto.request.UserCreateRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserDetailResponse;
import com.example.identityservice.model.User;

import java.util.List;

public interface UserService {
    long save(UserCreateRequest request);
    UserDetailResponse findById(Long id);
    List<UserDetailResponse> getAll();
    User update(Long id, UserUpdateRequest request);
    void delete(Long id);
}
