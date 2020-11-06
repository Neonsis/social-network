package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.exception.RecordNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.domain.user.security.Role;
import org.neonsis.socialnetwork.model.domain.user.security.RoleName;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.model.dto.mapper.UserMapper;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.persistence.repository.RoleRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.UserService;
import org.neonsis.socialnetwork.service.UuidGeneratorService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final UuidGeneratorService uuidGeneratorService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(UserDto userDto) {
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new InvalidWorkFlowException("User Role not set."));

        String uuid = uuidGeneratorService.generateUuid();
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = userMapper.userDtoToUser(userDto);
        user.setUuid(uuid);
        user.setEncryptedPassword(encryptedPassword);
        user.setRoles(Collections.singleton(userRole));
        User saved = userRepository.save(user);

        Profile profile = new Profile();
        profile.setUser(saved);
        profileRepository.save(profile);

        return userMapper.userToUserDto(saved);
    }

    @Override
    public UserDto findByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new RecordNotFoundException("User not found by uuid: " + uuid));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("User not found by email: " + email));
        return userMapper.userToUserDto(user);
    }
}
