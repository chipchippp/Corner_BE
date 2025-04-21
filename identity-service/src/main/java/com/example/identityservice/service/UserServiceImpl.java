package com.example.identityservice.service;

import com.example.identityservice.dto.request.UserCreateRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.exception.ResourceNotFoundException;
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

    @Override
    public User save(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            log.error("User with username {} already exists", request.getUsername());
            throw new RuntimeException(request.getUsername() + " already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("User with email {} already exists", request.getEmail());
            throw new RuntimeException(request.getEmail() + " already exists");
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .dob(request.getDob())
                .gender(request.getGender())
                .status(request.getStatus())
                .build();
        userRepository.save(user);
        log.info("User with username {} created", user.getUsername());
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = getUserById(id);
        log.info("User with id {} found", id);
        return user;
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
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setDob(request.getDob());
        user.setGender(request.getGender());
        user.setStatus(request.getStatus());
        userRepository.save(user);
        log.info("User with id {} updated", id);
        return user;
    }

    @Override
    public void delete(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
        log.info("User with id {} deleted", id);
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
