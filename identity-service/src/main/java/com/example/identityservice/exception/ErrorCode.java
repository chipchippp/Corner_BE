package com.example.identityservice.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USERNAME_ALREADY_EXISTS(1002, "Username already exists", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1003, "Email already exists", HttpStatus.BAD_REQUEST),
    PHONE_ALREADY_EXISTS(1004, "Phone number already exists", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1005, "Invalid credentials", HttpStatus.UNAUTHORIZED),
    USERNAME_OR_PASSWORD_INCORRECT(1006, "Username or password is incorrect", HttpStatus.UNAUTHORIZED),
    USERNAME_OR_EMAIL_INCORRECT(1007, "Username or email is incorrect", HttpStatus.UNAUTHORIZED),
    USERNAME_OR_PHONE_INCORRECT(1008, "Username or phone number is incorrect", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(1009, "Invalid token", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(1010, "Token expired", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED_EXCEPTION(1011, "An uncategorized error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    PASSWORD_MISMATCH(1012, "Password mismatch", HttpStatus.BAD_REQUEST),
    USER_NOT_ACTIVE(1013, "User is not active", HttpStatus.FORBIDDEN),
    USERNAME_INVALID(1014, "Username is invalid", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_EXISTS(1015, "Username not exists", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1016, "You do not have permission", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1017, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    PERMISSION_NOT_FOUND(1018, "Permission not found", HttpStatus.NOT_FOUND),
    PERMISSION_NAME_ALREADY_EXISTS(1019, "Permission name already exists", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1020, "Role not found", HttpStatus.NOT_FOUND),
    ROLE_NAME_ALREADY_EXISTS(1021, "Role name already exists", HttpStatus.BAD_REQUEST),
    ROLE_NOT_ACTIVE(1022, "Role is not active", HttpStatus.FORBIDDEN),
    CANNOT_DELETE_ADMIN_ROLE(1023, "Cannot delete admin role", HttpStatus.FORBIDDEN),
    ;

    private final int code;
    private final String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
