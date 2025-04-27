package com.example.identityservice.service;

import com.example.identityservice.dto.request.UserCreateRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserDetailResponse;
import com.example.identityservice.exception.*;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.model.User;
import com.example.identityservice.repository.UserRepository;
import com.example.identityservice.service.impl.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public long save(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            log.error("User with username {} already exists", request.getUsername());
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("User with email {} already exists", request.getEmail());
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User user = userMapper.toUser(request);
        userRepository.save(user);
        log.info("User with username {} created", user.getUsername());
        return user.getId();
    }

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
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            log.info("No users found");
        } else {
            log.info("Found {} users", users.size());
        }
        return users;
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
