package org.neonsis.socialnetwork.security.model.dto;

import lombok.Getter;
import lombok.Setter;


/**
 * UserDto.
 *
 * @author neonsis
 */
@Getter
@Setter
public class UserDto {

    /**
     * The user's account email.
     */
    private String email;

    /**
     * The user's account hashed password.
     */
    private String encryptedPassword;

    /**
     * JWT token
     */
    private String token;
}
