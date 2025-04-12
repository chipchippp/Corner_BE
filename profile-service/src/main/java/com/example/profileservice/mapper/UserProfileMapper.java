package com.example.profileservice.mapper;

import com.example.profileservice.dto.request.ProfileCreationRequest;
import com.example.profileservice.dto.response.UserProfileResponse;
import com.example.profileservice.model.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
