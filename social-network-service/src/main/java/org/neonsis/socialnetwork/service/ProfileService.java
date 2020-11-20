package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.user.ProfileDto;

public interface ProfileService {

    ProfileDto findByUserId(Long userId);

    ProfileDto update(ProfileDto profileDto);
}
