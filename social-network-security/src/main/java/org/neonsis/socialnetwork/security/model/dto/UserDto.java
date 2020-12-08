package org.neonsis.socialnetwork.security.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * UserDto.
 *
 * @author neonsis
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;

    /**
     * The user's account email.
     */
    private String email;

    /**
     * The user's account password.
     */
    private String password;

    /**
     * The user's account hashed password.
     */
    private String encryptedPassword;
}
