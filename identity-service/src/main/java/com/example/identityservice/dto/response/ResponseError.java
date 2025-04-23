package com.example.identityservice.dto.response;


public class ResponseError extends ApiResponse{
    public ResponseError(int code, String message) {
        super(code, message);
    }
}
