package org.neonsis.socialnetwork.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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

    /**
     * The user's birthday.
     */
    @NotNull(message = "{config.data.validation.profile_birthday_not_null}")
    private LocalDate birthday;

    /**
     * The user's gender.
     */
    @NotNull(message = "{config.data.validation.profile_gender_not_null}")
    private Gender gender;
}
