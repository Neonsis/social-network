package org.neonsis.socialnetwork.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.security.exception.EmailAlreadyExistsException;
import org.neonsis.socialnetwork.security.model.domain.Role;
import org.neonsis.socialnetwork.security.model.domain.RoleName;
import org.neonsis.socialnetwork.security.model.domain.User;
import org.neonsis.socialnetwork.security.model.dto.RegistrationDto;
import org.neonsis.socialnetwork.security.model.dto.UserDto;
import org.neonsis.socialnetwork.security.model.mapper.UserMapper;
import org.neonsis.socialnetwork.security.repository.RoleRepository;
import org.neonsis.socialnetwork.security.repository.UserRepository;
import org.neonsis.socialnetwork.security.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link User} service interface.
 *
 * @author neonsis
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(RegistrationDto registrationDto) {
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new InternalServerErrorException("Default ROLE_USER not found"));

        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new EmailAlreadyExistsException(
                    String.format("User with email '%s' already exists", registrationDto.getEmail())
            );
        }

        String encryptedPassword = passwordEncoder.encode(registrationDto.getPassword());

        User user = User.builder()
                .email(registrationDto.getEmail())
                .password(encryptedPassword)
                .roles(List.of(userRole))
                .build();


        userRepository.save(user);

        return this.toDto(user);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + id));
        return toDto(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found by email: " + email));
        return toDto(user);
    }

    private UserDto toDto(User user) {
        return userMapper.userToDto(user);
    }
}
