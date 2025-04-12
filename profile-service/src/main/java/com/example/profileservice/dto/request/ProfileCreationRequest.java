package com.example.profileservice.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreationRequest {
    String firstName;
    String lastName;
    String email;
    LocalDate dob;
    String city;
}
