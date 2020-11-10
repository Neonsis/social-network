package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.InternalServerException;
import org.neonsis.socialnetwork.exception.RecordNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.domain.user.security.Role;
import org.neonsis.socialnetwork.model.domain.user.security.RoleName;
import org.neonsis.socialnetwork.model.dto.ProfileDto;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.model.dto.mapper.ProfileMapper;
import org.neonsis.socialnetwork.model.dto.mapper.UserMapper;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.persistence.repository.RoleRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(UserDto userDto, ProfileDto profileDto) {
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new InternalServerException("User Role not set."));

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = userMapper.userDtoToUser(userDto);
        user.setEncryptedPassword(encryptedPassword);
        user.setRoles(Collections.singleton(userRole));
        User saved = userRepository.save(user);

        Profile profile = profileMapper.profileDtoToProfile(profileDto);
        profile.setUser(saved);
        profileRepository.save(profile);

        return userMapper.userToUserDto(saved);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User not found by id: " + id));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("User not found by email: " + email));
        return userMapper.userToUserDto(user);
    }
}
