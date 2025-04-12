package com.example.profileservice.service;

import com.example.profileservice.dto.request.ProfileCreationRequest;
import com.example.profileservice.dto.response.UserProfileResponse;
import com.example.profileservice.exception.ResourceNotFoundException;
import com.example.profileservice.mapper.UserProfileMapper;
import com.example.profileservice.model.UserProfile;
import com.example.profileservice.repository.UserProfileRepository;
import com.example.profileservice.service.impl.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;


    @Override
    public UserProfileResponse getAll() {
        return null;
    }

    @Override
    public UserProfileResponse getUserProfileId(String id) {
        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    @Override
    public UserProfileResponse saveProfile(ProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile = userProfileRepository.save(userProfile);
        log.info("User profile saved successfully: {}", userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

//    private UserProfile getUserById(long userId) {
//        return userProfileRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
//    }
}
