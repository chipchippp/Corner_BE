package com.example.identityservice.service;

import com.example.identityservice.dto.request.UserCreateRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserDetailResponse;
import com.example.identityservice.exception.*;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.model.User;
import com.example.identityservice.repository.UserRepository;
import com.example.identityservice.service.impl.UserService;
import com.example.identityservice.util.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetailResponse myProfile() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTS));
        return userMapper.toUserDetailResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public long save(UserCreateRequest request) {
        log.info("Creating user with username {}", request.getUsername());
        if (userRepository.existsByUsername(request.getUsername())) {
            log.error("User with username {} already exists", request.getUsername());
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("User with email {} already exists", request.getEmail());
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);
        userRepository.save(user);
        log.info("User with username {} created", user.getUsername());
        return user.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserDetailResponse findById(Long id) {
        UserDetailResponse userResponse = userMapper.toUserDetailResponse(getUserById(id));
        if (userResponse == null) {
            log.error("User with id {} not found", id);
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        log.info("User with id {} found", id);
        return userResponse;
    }

    @Override
    public List<UserDetailResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserDetailResponse).toList();
    }

    @Override
    public User update(Long id, UserUpdateRequest request) {
        User user = getUserById(id);

        // Check nếu username mới đã tồn tại và không phải là của user hiện tại
        if (!user.getUsername().equals(request.getUsername()) &&
                userRepository.existsByUsername(request.getUsername())) {
            log.error("Username {} already exists", request.getUsername());
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        // Check nếu email mới đã tồn tại và không phải là của user hiện tại
        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            log.error("Email {} already exists", request.getEmail());
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        userMapper.updateUser(request, user);

        userRepository.save(user);
        log.info("User with id {} updated successfully", id);
        return user;
    }

    @Override
    public void delete(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
        log.info("User with id {} deleted", id);
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}
