package com.example.identityservice.dto.request;

import com.example.identityservice.dto.validator.EnumPattern;
import com.example.identityservice.dto.validator.Gender;
import com.example.identityservice.dto.validator.GenderSubset;
import com.example.identityservice.util.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.example.identityservice.dto.validator.Gender.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class UserUpdateRequest {
    @NotBlank(message = "First name must not be blank")
    String firstName;
    @NotNull(message = "Last name must not be null")
    String lastName;
    @Email(message = "Email invalid format")
    String email;
    @NotNull(message = "Username must not be null")
    String username;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotNull(message = "Password must not be null")
    String password;

    @NotNull(message = "Date of birth must not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dob;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})
    Gender gender;

    @EnumPattern(name = "status", regexp = "^ACTIVE|INACTIVE|DELETED$", message = "Invalid status")
    UserStatus status;
}
