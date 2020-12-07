package org.neonsis.socialnetwork.model.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * LoginDto.
 *
 * @author neonsis
 */
@Getter
@Setter
public class LoginDto {

    /**
     * The user's email
     */
    @NotBlank
    private String email;

    /**
     * The user's password
     */
    @NotBlank
    private String password;
}
