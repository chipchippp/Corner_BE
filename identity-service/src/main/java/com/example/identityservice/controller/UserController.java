package com.example.identityservice.controller;

import com.example.identityservice.configuration.Translator;
import com.example.identityservice.dto.request.*;
import com.example.identityservice.dto.response.*;
import com.example.identityservice.exception.ResourceNotFoundException;
import com.example.identityservice.model.User;
import com.example.identityservice.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<Long> createUser(@RequestBody @Valid UserCreateRequest request) {
        log.info("Request add user = {} {}: ", request.getFirstName(), request.getLastName());
        try {
            long userId = userService.save(request);
            return new ApiResponse<>(HttpStatus.CREATED.value(), Translator.toLocale("user.add.success"), userId);
        } catch (ResourceNotFoundException e) {
            log.error("Error creating user: {}", e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("user.add.error"));
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDetailResponse> getUserById(@PathVariable Long id) {
        log.info("Get User by id = {} ", id);
        try {
            UserDetailResponse user = userService.findById(id);
            return new ApiResponse<>(HttpStatus.OK.value(), Translator.toLocale("user.list.success"), user);
        } catch (ResourceNotFoundException e) {
            log.error("User with id {} not found", id);
            return new ResponseError(HttpStatus.NOT_FOUND.value(), Translator.toLocale("user.not.found"));
        }
    }

    @GetMapping("/all")
    public ApiResponse<List<UserDetailResponse>> getAllUsers() {
        return ApiResponse.<List<UserDetailResponse>>builder()
                .data(userService.getAll())
                .message(Translator.toLocale("user.list.success"))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        log.info("Update User by id = {} ", id);
        try {
            log.info("Update User by id = {} ", id);
            userService.update(id, request);
            return new ApiResponse<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("user.update.success"));
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("user.update.error"));
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteUser(@PathVariable Long id) {
        log.info("Delete User by id = {} ", id);
        try {
            userService.delete(id);
            return new ApiResponse<>(HttpStatus.OK.value(), Translator.toLocale("user.delete.success"));
        } catch (ResourceNotFoundException e) {
            log.error("Error = {} ", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("user.not.found"));
        }
    }

}
