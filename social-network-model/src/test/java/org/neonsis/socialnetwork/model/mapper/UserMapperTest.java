package org.neonsis.socialnetwork.model.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.neonsis.socialnetwork.model.mapper.MapperDtoAssertUtilTest.*;

/**
 * {@link UserMapperTest} Unit Test.
 *
 * @author neonsis
 */
class UserMapperTest {

    /**
     * The tested mapper.
     */
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    /**
     * Test method to check correct mapping of {@link UserDto#email
     */
    @Test
    public void testUserToDtoEmail() {
        User expected = User.builder()
                .email("EMAIL")
                .build();

        UserDto actual = userMapper.userToDto(expected);

        assertEquals(expected.getEmail(), actual.getEmail());
    }

    /**
     * Test method to check correct mapping of {@link UserDto#encryptedPassword
     */
    @Test
    public void testUserToDtoPassword() {
        User expected = User.builder()
                .password("PASSWORD")
                .build();

        UserDto actual = userMapper.userToDto(expected);

        assertEquals(expected.getEncryptedPassword(), actual.getEncryptedPassword());
    }

    /**
     * Test method to check correct mapping of {@link UserDto#firstName
     */
    @Test
    public void testUserToDtoFirstName() {
        User expected = User.builder()
                .firstName("FIRST_NAME")
                .build();

        UserDto actual = userMapper.userToDto(expected);

        assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    /**
     * Test method to check correct mapping of {@link UserDto#lastName
     */
    @Test
    public void testUserToDtoLastName() {
        User expected = User.builder()
                .lastName("LAST_NAME")
                .build();

        UserDto actual = userMapper.userToDto(expected);

        assertEquals(expected.getLastName(), actual.getLastName());
    }

    /**
     * Test method to check correct mapping of {@link UserDto#avatar
     */
    @Test
    public void testUserToDtoAvatar() {
        User expected = User.builder()
                .avatar(createImage())
                .build();

        UserDto actual = userMapper.userToDto(expected);

        assertTrue(isEqualToImage(actual.getAvatar(), expected.getAvatar()));
    }

    /**
     * Test method to check correct mapping of {@link AbstractBaseDto} fields
     */
    @Test
    public void testUserToDtoBaseDto() {
        User expected = User.builder()
                .id(1L)
                .build();

        expected.setCreatedAt(Date.from(Instant.now()));
        expected.setUpdatedAt(Date.from(Instant.now()));

        UserDto actual = userMapper.userToDto(expected);

        assertTrue(isEqualToAbstractDto(actual, expected));
    }
}