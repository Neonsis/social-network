package org.neonsis.socialnetwork.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * RegistrationDto.
 *
 * @author neonsis
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    /**
     * The user's first name
     */
    @NotBlank(message = "{config.data.validation.user_first_name_not_blank}")
    private String firstName;

    /**
     * The user's last name
     */
    @NotBlank(message = "{config.data.validation.user_last_name_not_blank}")
    private String lastName;

    /**
     * The user's email
     */
    @Email(message = "{config.data.validation.user_email_valid}")
    private String email;

    /**
     * The user's password
     */
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "{config.data.validation.user_password_valid}")
    private String password;
}
