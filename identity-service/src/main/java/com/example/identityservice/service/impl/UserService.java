package com.example.identityservice.service.impl;

import com.example.identityservice.dto.request.UserCreateRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.model.User;

import java.util.List;

public interface UserService {
    User save(UserCreateRequest request);
    User findById(Long id);
    List<User> getAll();
    User update(Long id, UserUpdateRequest request);
    void delete(Long id);
}
