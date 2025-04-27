package com.example.identityservice.exception;

public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found"),
    USERNAME_ALREADY_EXISTS(1002, "Username already exists"),
    EMAIL_ALREADY_EXISTS(1003, "Email already exists"),
    PHONE_ALREADY_EXISTS(1004, "Phone number already exists"),
    INVALID_CREDENTIALS(1005, "Invalid credentials"),
    USERNAME_OR_PASSWORD_INCORRECT(1006, "Username or password is incorrect"),
    USERNAME_OR_EMAIL_INCORRECT(1007, "Username or email is incorrect"),
    USERNAME_OR_PHONE_INCORRECT(1008, "Username or phone number is incorrect"),
    INVALID_TOKEN(1009, "Invalid token"),
    TOKEN_EXPIRED(1010, "Token expired"),
    UNCATEGORIZED_EXCEPTION(1011, "An uncategorized error occurred"),
    PASSWORD_MISMATCH(1012, "Password mismatch"),
    USER_NOT_ACTIVE(1013, "User is not active"),
    USERNAME_INVALID(1014, "Username is invalid"),
    USERNAME_NOT_EXISTS(1015, "Username not exists"),;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
