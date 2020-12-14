package org.neonsis.socialnetwork.security.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.neonsis.socialnetwork.security.exception.EmailAlreadyExistsException;
import org.neonsis.socialnetwork.security.exception.UserNotFoundException;
import org.neonsis.socialnetwork.security.model.domain.User;
import org.neonsis.socialnetwork.security.model.dto.UserDto;
import org.neonsis.socialnetwork.security.model.mapper.UserMapper;
import org.neonsis.socialnetwork.security.repository.UserRepository;
import org.neonsis.socialnetwork.security.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        this.userService = new UserServiceImpl(
                userMapper,
                userRepository,
                passwordEncoder
        );
    }

    @Test
    public void testSignUpSuccess() {
        UserDto registrationDto = new UserDto();
        registrationDto.setEmail("test@gmail.com");
        registrationDto.setPassword("ENCRYPTED_PASSWORD");

        when(passwordEncoder.encode(registrationDto.getPassword())).thenReturn("ENCRYPTED_PASSWORD");
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);

        userService.signUp(registrationDto);

        ArgumentCaptor<User> userArgument = ArgumentCaptor.forClass(User.class);
        verify(passwordEncoder, times(1)).encode(registrationDto.getPassword());
        verify(userRepository, times(1)).save(userArgument.capture());
        verify(userRepository, times(1)).existsByEmail(registrationDto.getEmail());
        verifyNoMoreInteractions(userRepository);

        User actualUser = userArgument.getValue();

        assertEquals(registrationDto.getEmail(), actualUser.getEmail());
        assertEquals("ENCRYPTED_PASSWORD", actualUser.getEncryptedPassword());
    }

    @Test
    public void testSignUp_whenEmailExist_thenThrowException() {
        UserDto registrationDto = new UserDto();
        registrationDto.setEmail("test@gmail.com");

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        EmailAlreadyExistsException emailAlreadyExistsException
                = assertThrows(EmailAlreadyExistsException.class, () -> userService.signUp(registrationDto));

        String message = emailAlreadyExistsException.getMessage();

        assertEquals("User with email 'test@gmail.com' already exists", message);
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

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
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

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findByEmail(email);
        });

        String expected = "User not found by email: " + email;
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findByEmail(email);
    }

    public User createUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setEncryptedPassword("P4ssword");
        return user;
    }
}