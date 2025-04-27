package com.example.identityservice.mapper;

import com.example.identityservice.dto.request.UserCreateRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserDetailResponse;
import com.example.identityservice.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDetailResponse toUserDetailResponse(User user);
    User toUser(UserCreateRequest request);
    void updateUser(UserUpdateRequest request, @MappingTarget User user);
}
