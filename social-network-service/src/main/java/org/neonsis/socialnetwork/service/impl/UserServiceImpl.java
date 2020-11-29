package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InternalServerErrorException;
import org.neonsis.socialnetwork.exception.ValidationException;
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
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link User} service interface.
 *
 * @author neonsis
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(RegistrationDto registrationDto) {
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new InternalServerErrorException("Default ROLE_USER not found"));

        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new ValidationException(
                    "email",
                    String.format("User with email '%s' already exists", registrationDto.getEmail())
            );
        }

        String encryptedPassword = passwordEncoder.encode(registrationDto.getPassword());

        User user = User.builder()
                .email(registrationDto.getEmail())
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .password(encryptedPassword)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);

        Profile profile = Profile.builder()
                .user(user)
                .birthday(registrationDto.getBirthday())
                .gender(registrationDto.getGender())
                .build();

        profileRepository.save(profile);

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
