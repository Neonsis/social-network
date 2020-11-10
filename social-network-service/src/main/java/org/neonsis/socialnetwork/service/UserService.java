package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.ProfileDto;
import org.neonsis.socialnetwork.model.dto.UserDto;

public interface UserService {

    UserDto signUp(UserDto userDto, ProfileDto profileDto);

    UserDto findById(Long id);

    UserDto findByEmail(String email);
}
