package org.neonsis.socialnetwork.model.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.neonsis.socialnetwork.model.domain.user.Gender;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.neonsis.socialnetwork.model.mapper.MapperDtoAssertUtilTest.isEqualToAbstractDto;

/**
 * {@link ProfileMapperTest} Unit Test.
 *
 * @author neonsis
 */
class ProfileMapperTest {

    /**
     * The tested mapper.
     */
    private final ProfileMapper profileMapper = Mappers.getMapper(ProfileMapper.class);

    /**
     * Test method to check correct mapping of {@link ProfileDto#birthday
     */
    @Test
    public void testProfileToDtoBirthday() {
        Profile expected = Profile.builder()
                .birthday(LocalDate.now())
                .build();

        ProfileDto actual = profileMapper.profileToDto(expected);

        assertEquals(expected.getBirthday(), actual.getBirthday());
    }

    /**
     * Test method to check correct mapping of {@link ProfileDto#gender
     */
    @Test
    public void testProfileToDtoGender() {
        Profile expected = Profile.builder()
                .gender(Gender.MALE)
                .build();

        ProfileDto actual = profileMapper.profileToDto(expected);

        assertEquals(expected.getGender(), actual.getGender());
    }

    /**
     * Test method to check correct mapping of {@link ProfileDto#about
     */
    @Test
    public void testProfileToDtoAbout() {
        Profile expected = Profile.builder()
                .about("ABOUT")
                .build();

        ProfileDto actual = profileMapper.profileToDto(expected);

        assertEquals(expected.getAbout(), actual.getAbout());
    }

    /**
     * Test method to check correct mapping of {@link ProfileDto#country
     */
    @Test
    public void testProfileToDtoCountry() {
        Profile expected = Profile.builder()
                .country("COUNTRY")
                .build();

        ProfileDto actual = profileMapper.profileToDto(expected);

        assertEquals(expected.getCountry(), actual.getCountry());
    }

    /**
     * Test method to check correct mapping of {@link ProfileDto#city
     */
    @Test
    public void testProfileToDtoCity() {
        Profile expected = Profile.builder()
                .city("CITY")
                .build();

        ProfileDto actual = profileMapper.profileToDto(expected);

        assertEquals(expected.getCity(), actual.getCity());
    }

    /**
     * Test method to check correct mapping of {@link AbstractBaseDto} fields
     */
    @Test
    public void testProfileToDtoBaseDto() {
        Profile expected = Profile.builder()
                .id(1L)
                .build();

        expected.setCreatedAt(Date.from(Instant.now()));
        expected.setUpdatedAt(Date.from(Instant.now()));

        ProfileDto actual = profileMapper.profileToDto(expected);

        assertTrue(isEqualToAbstractDto(actual, expected));
    }
}