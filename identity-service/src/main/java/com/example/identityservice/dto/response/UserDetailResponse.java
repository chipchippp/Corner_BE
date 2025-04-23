package com.example.identityservice.dto.response;

import com.example.identityservice.dto.validator.Gender;
import com.example.identityservice.util.UserStatus;
import lombok.*;

import java.io.Serializable;
@Getter
@Builder
@AllArgsConstructor
public class UserDetailResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private Gender gender;
    private UserStatus status;

}
