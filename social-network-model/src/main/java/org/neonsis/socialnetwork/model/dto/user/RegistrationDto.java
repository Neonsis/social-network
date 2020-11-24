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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    @NotBlank(message = "{config.data.validation.user_first_name_not_blank}")
    private String firstName;

    @NotBlank(message = "{config.data.validation.user_last_name_not_blank}")
    private String lastName;

    @Email(message = "{config.data.validation.user_email_valid}")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "{config.data.validation.user_password_valid}")
    private String password;

    @NotNull(message = "{config.data.validation.user_gender_not_null}")
    private Gender gender;

    @NotNull(message = "{config.data.validation.user_birthday_not_null}")
    private LocalDate birthday;
}
