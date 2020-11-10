package org.neonsis.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.neonsis.socialnetwork.exception.RecordNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.Gender;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.dto.ProfileDto;
import org.neonsis.socialnetwork.model.dto.mapper.ProfileMapper;
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

    /*@Test
    public void testFindById_whenExists_shouldReturnProfile() {
        Profile expected = createProfile();
        when(profileRepository.findById(1L)).thenReturn(Optional.of(expected));

        ProfileDto profile = profileService.findByUserId(1L);

        assertNotNull(profile);
        assertEquals(1L, profile.getId());

        verify(profileRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_whenNotExists_shouldThrowException() {
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            profileService.findByUserId(1L);
        });

        String expected = "Profile not found by id: 1";
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(profileRepository, times(1)).findById(1L);
    }*/

    @Test
    public void testUpdateProfile_whenExists_shouldReturnUpdatedProfile() {
        Profile oldProfile = createProfile();
        ProfileDto expectedProfileDto = createProfileDto();
        Profile updatedProfile = profileMapper.profileDtoToProfile(expectedProfileDto);

        when(profileRepository.save(oldProfile)).thenReturn(updatedProfile);
        when(profileRepository.findById(1L)).thenReturn(Optional.of(oldProfile));

        ProfileDto updateProfile = profileService.updateProfile(expectedProfileDto, 1L);

        assertEquals("Minsk", updateProfile.getCity());
        assertEquals("Belarus", updateProfile.getCountry());
        assertEquals(Gender.FEMALE, updateProfile.getGender());
        assertEquals("About", updateProfile.getAbout());

        verify(profileRepository, times(1)).findById(1L);
        verify(profileRepository, times(1)).save(oldProfile);
    }

    @Test
    public void testUpdateProfile_whenNotExists_shouldThrowException() {
        ProfileDto profileDto = new ProfileDto();
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            profileService.updateProfile(profileDto, 1L);
        });

        String expected = "Profile not found by id: 1";
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(profileRepository, times(1)).findById(1L);
        verify(profileRepository, times(0)).save(any(Profile.class));
    }

    public ProfileDto createProfileDto() {
        return ProfileDto.builder()
                .id(1L)
                .city("Minsk")
                .country("Belarus")
                .gender(Gender.FEMALE)
                .about("About")
                .build();
    }

    public Profile createProfile() {
        Profile profile = new Profile();
        profile.setId(1L);
        return profile;
    }
}