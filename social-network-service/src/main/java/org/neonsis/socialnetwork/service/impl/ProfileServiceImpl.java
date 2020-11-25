package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.dto.mapper.ProfileMapper;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
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
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by user id: " + userId));
        return toDto(profile);
    }

    @Override
    public ProfileDto update(ProfileDto profileDto) {
        Long id = profileDto.getId();
        profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id: " + id));

        Profile saved = profileRepository.save(toEntity(profileDto));

        return toDto(saved);
    }

    private ProfileDto toDto(Profile profile) {
        return profileMapper.profileToDto(profile);
    }

    private Profile toEntity(ProfileDto profileDto) {
        return profileMapper.dtoToProfile(profileDto);
    }
}
