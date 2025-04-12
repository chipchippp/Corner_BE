package com.example.profileservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Node("user_profile")
public class UserProfile{
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    String id;
    @Property("user_id")
    String userId;
    @Property("first_name")
    String firstName;
    @Property("last_name")
    String lastName;
    @Property("email")
    String email;
    @Property("phone_number")
    String phoneNumber;

    LocalDate dob;
    String city;
}
