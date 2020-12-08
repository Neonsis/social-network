package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileUpdateDto;
import org.neonsis.socialnetwork.model.dto.user.RegistrationDto;
import org.neonsis.socialnetwork.model.mapper.ProfileMapper;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.security.facade.AuthenticationFacade;
import org.neonsis.socialnetwork.security.model.domain.User;
import org.neonsis.socialnetwork.security.model.dto.UserDto;
import org.neonsis.socialnetwork.security.repository.UserRepository;
import org.neonsis.socialnetwork.security.service.UserService;
import org.neonsis.socialnetwork.service.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link Profile} service interface.
 *
 * @author neonsis
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileMapper profileMapper;

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    private final UserService userService;

    @Override
    public ProfileDto findByUserId(Long userId) {
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by user id: " + userId));
        return toDto(profile);
    }

    @Override
    public ProfileDto update(ProfileUpdateDto profileUpdateDto) {
        Long id = authenticationFacade.getLoggedInUserId();
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id: " + id));

        if (StringUtils.isNotBlank(profileUpdateDto.getAbout())) {
            profile.setAbout(profileUpdateDto.getAbout());
        }
        if (StringUtils.isNotBlank(profileUpdateDto.getCity())) {
            profile.setCity(profileUpdateDto.getCity());
        }
        if (StringUtils.isNotBlank(profileUpdateDto.getCountry())) {
            profile.setCountry(profileUpdateDto.getCountry());
        }
        if (profileUpdateDto.getBirthday() != null) {
            profile.setBirthday(profileUpdateDto.getBirthday());
        }
        if (profileUpdateDto.getGender() != null) {
            profile.setGender(profileUpdateDto.getGender());
        }

        profileRepository.save(profile);

        return toDto(profile);
    }

    @Override
    public ProfileDto signUp(RegistrationDto registrationDto) {
        UserDto userDto =new UserDto();
        userDto.setEmail(registrationDto.getEmail());
        userDto.setPassword(registrationDto.getPassword());

        UserDto registeredUser = userService.signUp(userDto);

        User user = userRepository.findById(registeredUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + registeredUser.getId()));

        Profile profile = Profile.builder()
                .user(user)
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .birthday(registrationDto.getBirthday())
                .gender(registrationDto.getGender())
                .build();

        profileRepository.save(profile);

        return toDto(profile);
    }

    @Override
    public ProfileDto getLoggedInProfile() {
        Long userId = authenticationFacade.getLoggedInUserId();
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id: " + userId));

        return toDto(profile);
    }

    private ProfileDto toDto(Profile profile) {
        return profileMapper.profileToDto(profile);
    }
}
