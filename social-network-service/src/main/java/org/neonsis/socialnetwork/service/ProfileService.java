package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.ProfileDto;

public interface ProfileService {

    ProfileDto findByUserId(Long userId);

    ProfileDto updateProfile(ProfileDto profileDto, Long userId);
}
