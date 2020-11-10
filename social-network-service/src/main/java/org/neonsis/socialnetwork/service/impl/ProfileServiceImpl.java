package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.RecordNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.dto.ProfileDto;
import org.neonsis.socialnetwork.model.dto.mapper.ProfileMapper;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.service.ProfileService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    @Override
    public ProfileDto findByUserId(Long userId) {
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("Profile not found by user id: " + userId));
        return profileMapper.profileToProfileDto(profile);
    }

    @Override
    public ProfileDto updateProfile(ProfileDto profileDto, Long userId) {
        profileRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("Profile not found by user id: " + userId));

        Profile profile = profileMapper.profileDtoToProfile(profileDto);
        profile.setId(userId);
        Profile saved = profileRepository.save(profile);

        return profileMapper.profileToProfileDto(saved);
    }
}
