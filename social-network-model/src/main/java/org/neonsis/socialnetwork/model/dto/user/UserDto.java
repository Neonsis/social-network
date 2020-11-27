package org.neonsis.socialnetwork.model.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;


/**
 * UserDto.
 *
 * @author neonsis
 */
@Getter
@Setter
public class UserDto extends AbstractBaseDto {

    /**
     * The user's account email.
     */
    private String email;

    /**
     * The user's account hashed password.
     */
    private String encryptedPassword;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's lsat name.
     */
    private String lastName;

    /**
     * The user's main avatar.
     */
    private ImageDto avatar;

    /**
     * JWT token
     */
    private String token;
}
