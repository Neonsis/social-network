package org.neonsis.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InternalServerErrorException;
import org.neonsis.socialnetwork.exception.ValidationException;
import org.neonsis.socialnetwork.model.domain.user.Gender;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.domain.user.security.Role;
import org.neonsis.socialnetwork.model.domain.user.security.RoleName;
import org.neonsis.socialnetwork.model.mapper.UserMapper;
import org.neonsis.socialnetwork.model.dto.user.RegistrationDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.persistence.repository.RoleRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        this.userService = new UserServiceImpl(
                userMapper,
                userRepository,
                profileRepository,
                roleRepository,
                passwordEncoder
        );
    }

    @Test
    public void testSignUpSuccess() {
        Role role = Role.builder().name(RoleName.ROLE_USER).build();
        RegistrationDto registrationDto = new RegistrationDto(
                "Andrey",
                "Vinel",
                "test@gmail.com",
                "P4ssword",
                Gender.MALE,
                LocalDate.of(2002, 9, 29)
        );

        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(registrationDto.getPassword())).thenReturn("ENCRYPTED_PASSWORD");
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);


        userService.signUp(registrationDto);

        ArgumentCaptor<User> userArgument = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Profile> profileArgument = ArgumentCaptor.forClass(Profile.class);
        verify(roleRepository, times(1)).findByName(RoleName.ROLE_USER);
        verify(passwordEncoder, times(1)).encode(registrationDto.getPassword());
        verify(userRepository, times(1)).save(userArgument.capture());
        verify(userRepository, times(1)).existsByEmail(registrationDto.getEmail());
        verify(profileRepository, times(1)).save(profileArgument.capture());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(profileRepository);

        User actualUser = userArgument.getValue();
        Profile actualProfile = profileArgument.getValue();

        assertEquals(registrationDto.getEmail(), actualUser.getEmail());
        assertEquals(registrationDto.getFirstName(), actualUser.getFirstName());
        assertEquals(registrationDto.getLastName(), actualUser.getLastName());
        assertEquals("ENCRYPTED_PASSWORD", actualUser.getEncryptedPassword());
        assertEquals(Collections.singleton(role), actualUser.getRoles());
        assertEquals(registrationDto.getGender(), actualProfile.getGender());
        assertEquals(registrationDto.getBirthday(), actualProfile.getBirthday());
    }

    @Test
    public void testSignUp_whenEmailExist_thenThrowException() {
        Role role = Role.builder().name(RoleName.ROLE_USER).build();
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@gmail.com");

        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(role));
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        ValidationException validationException
                = assertThrows(ValidationException.class, () -> userService.signUp(registrationDto));

        String message = validationException.getMessage();

        assertEquals("User with email 'test@gmail.com' already exists", message);
    }

    @Test
    public void testSignUpRoleNotFound() {
        assertThrows(InternalServerErrorException.class, () -> userService.signUp(null));
    }

    @Test
    public void testFindById_whenExists_shouldReturnUser() {
        User user = createUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto actual = userService.findById(1L);

        assertNotNull(actual);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_whenNotExists_shouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.findById(1L);
        });

        String expected = "User not found by id: 1";
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByEmail_whenExists_shouldReturnUser() {
        User user = createUser();
        String email = "test@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDto actual = userService.findByEmail(email);

        assertNotNull(actual);

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testFindByEmail_whenNotExists_shouldReturnUser() {
        String email = "test@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.findByEmail(email);
        });

        String expected = "User not found by email: " + email;
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findByEmail(email);
    }

    public User createUser() {
        return User.builder()
                .email("test@gmail.com")
                .firstName("Andrey")
                .lastName("Vinel")
                .password("P4ssword")
                .build();
    }
}