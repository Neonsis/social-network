package org.neonsis.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.neonsis.socialnetwork.exception.RecordNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.model.dto.mapper.ProfileMapper;
import org.neonsis.socialnetwork.model.dto.mapper.UserMapper;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.persistence.repository.RoleRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.UserService;
import org.neonsis.socialnetwork.service.UuidGeneratorService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final ProfileMapper profileMapper = Mappers.getMapper(ProfileMapper.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UuidGeneratorService uuidGeneratorService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        this.userService = new UserServiceImpl(
                userMapper,
                profileMapper,
                userRepository,
                profileRepository,
                roleRepository,
                uuidGeneratorService,
                passwordEncoder
        );
    }

    @Test
    public void testFindByUuid_whenExists_shouldReturnUser() {
        User user = createUser();
        String uuid = "andrey-vinel";
        when(userRepository.findByUuid(uuid)).thenReturn(Optional.of(user));

        UserDto actual = userService.findByUuid(uuid);

        assertNotNull(actual);

        verify(userRepository, times(1)).findByUuid(uuid);
    }

    @Test
    public void testFindByUuid_whenNotExists_shouldReturnUser() {
        String uuid = "andrey-vinel";
        when(userRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            userService.findByUuid(uuid);
        });

        String expected = "User not found by uuid: " + uuid;
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findByUuid(uuid);
    }

    @Test
    public void testFindByEmail_whenExists_shouldReturnUser() {
        User user = createUser();
        String email = "test@gmail.ru";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDto actual = userService.findByEmail(email);

        assertNotNull(actual);

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testFindByEmail_whenNotExists_shouldReturnUser() {
        String email = "test@gmail.ru";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            userService.findByEmail(email);
        });

        String expected = "User not found by email: " + email;
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findByEmail(email);
    }

    public User createUser() {
        User user = new User();
        user.setEmail("test@gmail.ru");
        user.setFirstName("Andrey");
        user.setLastName("Vinel");
        user.setEncryptedPassword("P4ssword");
        user.setUuid("andrey-vinel");
        return user;
    }
}