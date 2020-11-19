package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.user.RegistrationDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

public interface UserService {

    UserDto signUp(RegistrationDto registrationDto);

    UserDto findById(Long id);

    UserDto findByEmail(String email);
}
