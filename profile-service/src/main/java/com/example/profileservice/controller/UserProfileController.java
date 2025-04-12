package com.example.profileservice.controller;

import com.example.profileservice.dto.request.ProfileCreationRequest;
import com.example.profileservice.dto.response.UserProfileResponse;
import com.example.profileservice.service.impl.UserProfileService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user-profile")
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/")
    public UserProfileResponse getAll() {
        return userProfileService.getAll();
    }

    @GetMapping("/{profileId}")
    public UserProfileResponse getUserProfile(@PathVariable String profileId) {
        return userProfileService.getUserProfileId(profileId);
    }

    @PostMapping("/save")
    public UserProfileResponse saveProfile(@RequestBody ProfileCreationRequest request) {
        return userProfileService.saveProfile(request);
    }

}
