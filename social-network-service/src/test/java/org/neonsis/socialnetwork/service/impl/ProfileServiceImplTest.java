package org.neonsis.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.mapper.ProfileMapper;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.service.ProfileService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    private final ProfileMapper profileMapper = Mappers.getMapper(ProfileMapper.class);

    private ProfileService profileService;

    @BeforeEach
    public void setUp() {
        this.profileService = new ProfileServiceImpl(
                profileMapper,
                profileRepository
        );
    }

    @Test
    public void testFindByIdSuccess() {
        Profile expected = buildTestEntity();
        when(profileRepository.findById(1L)).thenReturn(Optional.of(expected));

        ProfileDto profile = profileService.findByUserId(1L);

        assertNotNull(profile);
        assertEquals(1L, profile.getId());

        verify(profileRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdFailure() {
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            profileService.findByUserId(1L);
        });

        String expected = "Profile not found by user id: 1";
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(profileRepository, times(1)).findById(1L);
    }


    @Test
    public void testUpdateProfileSuccess() {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(1L);
        profileDto.setAbout("TEST");

        when(profileRepository.findById(1L)).thenReturn(Optional.of(buildTestEntity()));

        profileService.update(profileDto);

        ArgumentCaptor<Profile> argument = ArgumentCaptor.forClass(Profile.class);
        verify(profileRepository, times(1)).save(argument.capture());

        Profile value = argument.getValue();

        assertEquals(profileDto.getId(), value.getId());
        assertEquals(profileDto.getAbout(), value.getAbout());
    }

    @Test
    public void testUpdateProfileFailure() {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(1L);
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            profileService.update(profileDto);
        });

        String expected = "Profile not found by id: 1";
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(profileRepository, times(1)).findById(1L);
        verify(profileRepository, times(0)).save(any(Profile.class));
    }


    public Profile buildTestEntity() {
        Profile profile = new Profile();
        profile.setId(1L);
        return profile;
    }
}
