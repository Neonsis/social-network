package org.neonsis.socialnetwork.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.security.exception.EmailAlreadyExistsException;
import org.neonsis.socialnetwork.security.model.domain.User;
import org.neonsis.socialnetwork.security.model.dto.UserDto;
import org.neonsis.socialnetwork.security.model.mapper.UserMapper;
import org.neonsis.socialnetwork.security.repository.UserRepository;
import org.neonsis.socialnetwork.security.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

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

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyExistsException(
                    String.format("User with email '%s' already exists", userDto.getEmail())
            );
        }

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setEncryptedPassword(encryptedPassword);

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
