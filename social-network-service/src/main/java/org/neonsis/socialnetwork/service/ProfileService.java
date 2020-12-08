package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileUpdateDto;
import org.neonsis.socialnetwork.model.dto.user.RegistrationDto;

/**
 * {@link Profile} service interface.
 *
 * @author neonsis
 */
public interface ProfileService {

    /**
     * Find profile by user id.
     *
     * @param userId the id of the searched profile.
     * @return the founded profile.
     * @throws EntityNotFoundException if profile not found.
     */
    ProfileDto findByUserId(Long userId);

    /**
     * Update a profile.
     *
     * @param profileUpdateDto a profile object describing the profile to updated.
     * @return the updated profile.
     * @throws EntityNotFoundException if profile not found.
     */
    ProfileDto update(ProfileUpdateDto profileUpdateDto);

    ProfileDto signUp(RegistrationDto registrationDto);

    ProfileDto getLoggedInProfile();
}
