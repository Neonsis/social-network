package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.RecordNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.model.dto.mapper.UserMapper;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto signUp(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto signIn(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto findByUuid(String uuid) {
        User user = userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new RecordNotFoundException("User not found by uuid: " + uuid));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto getAuthUser() {
        return null;
    }
}
