package com.example.identityservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ApiResponse<T> {
    int code;
    String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;


    //    Put, Patch, Delete
    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    //    Get, Post
    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse() {

    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
