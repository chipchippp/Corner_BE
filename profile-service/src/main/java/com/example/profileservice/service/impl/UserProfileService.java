package com.example.profileservice.service.impl;

import com.example.profileservice.dto.request.ProfileCreationRequest;
import com.example.profileservice.dto.response.UserProfileResponse;

public interface UserProfileService{
    UserProfileResponse getAll();
    UserProfileResponse getUserProfileId(String id);
    UserProfileResponse saveProfile(ProfileCreationRequest request);
}
